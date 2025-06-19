package com.ecs.sms.features.auth.data.user

import com.ecs.sms.features.auth.domain.UserRepository
import com.ecs.sms.features.auth.domain.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val jpaRepository: UserJpaRepository,
    private val jpaMapper: UserJpaMapper
) : UserRepository {
    override fun findUserByEmail(email: String): User? {
        val user = jpaRepository.findByEmail(email) ?: return null
        return jpaMapper.toDomain(user)
    }

    override fun findById(id: String): User? {
        val userEntity = jpaRepository.findById(id).orElse(null) ?: return null
        return jpaMapper.toDomain(userEntity)
    }

    override fun save(user: User): User {
        val userEntity = jpaRepository.save(jpaMapper.toEntity(user))
        return jpaMapper.toDomain(userEntity)
    }
}

interface UserJpaRepository : JpaRepository<UserEntity, String> {
    fun findByEmail(email: String): UserEntity?
}
