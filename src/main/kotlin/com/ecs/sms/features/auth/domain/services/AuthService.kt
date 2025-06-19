package com.ecs.sms.features.auth.domain.services

import com.ecs.sms.features.auth.domain.RefreshTokenRepository
import com.ecs.sms.features.auth.domain.UserRepository
import com.ecs.sms.features.auth.domain.models.RefreshToken
import com.ecs.sms.features.auth.domain.models.Role
import com.ecs.sms.features.auth.domain.models.User
import com.ecs.sms.features.auth.infrastructure.HashEncoder
import com.ecs.sms.features.profile.data.ProfilesRepository
import com.ecs.sms.features.profile.domain.Profile
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.time.Instant
import java.util.*

@Service
class AuthService(
    private val profilesRepository: ProfilesRepository,
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val hashEncoder: HashEncoder,
) {
    fun register(email: String, password: String): User {
        val user = userRepository.save(
            User(
                email = email,
                hashedPassword = hashEncoder.encode(password),
                roles = listOf(Role.SUPERVISOR)
            )
        )
        profilesRepository.save(Profile(user.id, user.email.substringBefore("@")))
        return user
    }

    fun login(email: String, password: String): AuthData {
        val user = userRepository.findUserByEmail(email) ?: throw BadCredentialsException("Invalid credentials.")

        if (!hashEncoder.matches(password, user.hashedPassword)) {
            throw BadCredentialsException("Invalid credentials.")
        }

        val newAccessToken = tokenService.generateAccessToken(user)
        val newRefreshToken = tokenService.generateRefreshToken(user)

        storeRefreshToken(userId = user.id, rawRefreshToken = newRefreshToken)

        return AuthData(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
            user = user
        )
    }

    fun refreshToken(refreshToken: String): AuthData {
        if (!tokenService.validateRefreshToken(refreshToken))
            throw BadCredentialsException("Invalid credentials.")

        val userId = tokenService.getUserIdFromToken(refreshToken)
        val user = userRepository.findById(userId)
            ?: throw IllegalArgumentException("Invalid refresh token.")

        val hashed = hashToken(refreshToken)
        refreshTokenRepository.findByUserIdAndHashedToken(userId = user.id, hashedToken = hashed)
            ?: throw IllegalArgumentException(
                "Refresh token not recognized (maybe used or expired?)"
            )

        refreshTokenRepository.deleteByUserIdAndHashedToken(userId = user.id, hashedToken = hashed)

        val newAccessToken = tokenService.generateAccessToken(user)
        val newRefreshToken = tokenService.generateRefreshToken(user)

        storeRefreshToken(userId = user.id, rawRefreshToken = newRefreshToken)

        return AuthData(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
            user = user
        )
    }

    fun deleteRefreshToken(refreshToken: String) {
        if (!tokenService.validateRefreshToken(refreshToken)) {
            return // Token already invalid; nothing to do
        }

        val userId = tokenService.getUserIdFromToken(refreshToken)
        val hashed = hashToken(refreshToken)

        refreshTokenRepository.deleteByUserIdAndHashedToken(userId = userId, hashedToken = hashed)
    }


    private fun storeRefreshToken(userId: String, rawRefreshToken: String) {
        val hashed = hashToken(rawRefreshToken)
        val expiryMs = tokenService.refreshTokenValidityMs
        val expiresAt = Instant.now().plusMillis(expiryMs)

        refreshTokenRepository.save(
            RefreshToken(userId = userId, expiresAt = expiresAt, hashedToken = hashed)
        )
    }

    private fun hashToken(token: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(token.toByteArray())
        return Base64.getEncoder().encodeToString(hashBytes)
    }

    fun getRefreshTokenValiditySeconds(): Long {
        return tokenService.refreshTokenValidityMs / 1000
    }
}

data class AuthData(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)
