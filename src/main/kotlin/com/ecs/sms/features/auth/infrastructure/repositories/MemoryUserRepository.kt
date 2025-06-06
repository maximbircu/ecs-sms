package com.ecs.sms.features.auth.infrastructure.repositories

import com.ecs.sms.features.auth.domain.UserRepository
import com.ecs.sms.features.auth.domain.models.User
import org.springframework.stereotype.Repository

@Repository
class MemoryUserRepository : UserRepository {
    private val users = mutableListOf<User>()


    override fun findUserByEmail(email: String): User? {
        return users.firstOrNull { it.email == email }
    }

    override fun findById(id: String): User? {
        return users.firstOrNull { it.id == id }
    }

    override fun save(user: User): User {
        users.add(user)
        return user
    }
}