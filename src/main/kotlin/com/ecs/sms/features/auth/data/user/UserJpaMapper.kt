package com.ecs.sms.features.auth.data.user

import com.ecs.sms.features.auth.domain.models.User
import org.springframework.stereotype.Component

@Component
class UserJpaMapper {
    fun toDomain(entity: UserEntity): User = User(
        id = entity.id,
        email = entity.email,
        hashedPassword = entity.hashedPassword,
        roles = entity.roles.toList()
    )

    fun toEntity(domain: User): UserEntity = UserEntity(
        id = domain.id,
        email = domain.email,
        hashedPassword = domain.hashedPassword,
        roles = domain.roles.toMutableSet()
    )
}
