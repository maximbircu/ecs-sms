package com.ecs.sms.features.auth.domain.services

import com.ecs.sms.features.auth.domain.models.Role
import com.ecs.sms.features.auth.domain.models.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class TokenService(
    @Value("\${jwt.secret}") private val secret: String
) {
    private val secretKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret))
    }
    private val accessTokenValidityMs = 15L * 60L * 1000L
    val refreshTokenValidityMs = 30L * 24L * 60 * 60L * 10000L

    private fun generateToken(
        user: User,
        type: String,
        expiry: Long,
    ): String {
        val now = Date()
        val expiryDate = Date(now.time + expiry)
        return Jwts.builder()
            .subject(user.id)
            .claim("email", user.email)
            .claim("roles", user.roles.map { it.name })
            .claim("type", type)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
    }

    fun generateAccessToken(user: User): String {
        return generateToken(user, "access", accessTokenValidityMs)
    }

    fun generateRefreshToken(user: User): String {
        return generateToken(user, "refresh", refreshTokenValidityMs)
    }

    fun validateAccessToken(token: String): Boolean {
        val claims = parseAllClaims(token) ?: return false
        val tokenType = claims["type"] as? String ?: return false
        return tokenType == "access"
    }

    fun validateRefreshToken(token: String): Boolean {
        val claims = parseAllClaims(token) ?: return false
        val tokenType = claims["type"] as? String ?: return false
        return tokenType == "refresh"
    }

    fun getUserIdFromToken(token: String): String {
        val claims = parseAllClaims(token) ?: throw IllegalArgumentException("Invalid token")
        return claims.subject
    }

    fun getClaimsFromToken(token: String): TokenClaims {
        return TokenClaims(parseAllClaims(token) ?: throw IllegalArgumentException("Invalid token"))
    }

    private fun parseAllClaims(token: String): Claims? {
        val rawToken = if (token.startsWith("Bearer ")) {
            token.removePrefix("Bearer ")
        } else token
        return try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(rawToken)
                .payload
        } catch (ex: Exception) {
            return null
        }
    }
}

data class TokenClaims(val claims: Claims) {
    val userId: String = claims.subject
    val email: String = claims["email"] as String
    val roles: List<Role> = (claims["roles"] as? List<*>)?.mapNotNull {
        (it as? String)?.let { Role.valueOf(it) }
    } ?: emptyList()
}