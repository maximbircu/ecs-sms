package com.ecs.sms.features.auth.data.token

import com.ecs.sms.features.auth.domain.RefreshTokenRepository
import com.ecs.sms.features.auth.domain.models.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class RefreshTokenRepositoryImpl(
    private val jpaRepository: RefreshTokenJpaRepository,
    private val jpaMapper: RefreshTokenJpaMapper
) : RefreshTokenRepository {
    override fun findByUserIdAndHashedToken(
        userId: String,
        hashedToken: String
    ): RefreshToken? {
        return jpaRepository.findByHashedToken(hashedToken)
            ?.takeIf { it.userId == userId }
            ?.let(jpaMapper::toDomain)
    }

    override fun deleteByUserIdAndHashedToken(userId: String, hashedToken: String) {
        val token = jpaRepository.findByHashedToken(hashedToken)
        if (token != null && token.userId == userId) {
            jpaRepository.delete(token)
        }
    }

    override fun save(refreshToken: RefreshToken) {
        jpaRepository.save(jpaMapper.toEntity(refreshToken))
    }

}

interface RefreshTokenJpaRepository : JpaRepository<RefreshTokenEntity, String> {
    fun findByHashedToken(hashedToken: String): RefreshTokenEntity?
}
