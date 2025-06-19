package com.ecs.sms.features.auth.data.token

import com.ecs.sms.features.auth.domain.models.RefreshToken
import org.springframework.stereotype.Component

@Component
class RefreshTokenJpaMapper {

    fun toDomain(entity: RefreshTokenEntity): RefreshToken = RefreshToken(
        id = entity.id,
        userId = entity.userId,
        expiresAt = entity.expiresAt,
        hashedToken = entity.hashedToken,
        createdAt = entity.createdAt
    )

    fun toEntity(domain: RefreshToken): RefreshTokenEntity = RefreshTokenEntity(
        id = domain.id,
        userId = domain.userId,
        expiresAt = domain.expiresAt,
        hashedToken = domain.hashedToken,
        createdAt = domain.createdAt
    )
}
