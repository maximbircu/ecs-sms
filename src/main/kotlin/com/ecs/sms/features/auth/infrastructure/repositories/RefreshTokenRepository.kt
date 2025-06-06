package com.ecs.sms.features.auth.infrastructure.repositories

import com.ecs.sms.features.auth.domain.RefreshTokenRepository
import com.ecs.sms.features.auth.domain.models.RefreshToken
import org.springframework.stereotype.Repository

@Repository
class MemoryRefreshTokenRepository : RefreshTokenRepository {
    private val tokens: MutableList<RefreshToken> = mutableListOf()

    override fun findByUserIdAndHashedToken(userId: String, hashedToken: String): RefreshToken? {
        return tokens.firstOrNull { it.userId == userId && it.hashedToken == hashedToken }
    }

    override fun deleteByUserIdAndHashedToken(userId: String, hashedToken: String) {
        tokens.removeAll { it.userId == userId && it.hashedToken == hashedToken }
    }

    override fun save(refreshToken: RefreshToken) {
        tokens.add(refreshToken)
    }
}