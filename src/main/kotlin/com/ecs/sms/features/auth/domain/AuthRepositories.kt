package com.ecs.sms.features.auth.domain

import com.ecs.sms.features.auth.domain.models.RefreshToken
import com.ecs.sms.features.auth.domain.models.User

interface RefreshTokenRepository {
    fun findByUserIdAndHashedToken(userId: String, hashedToken: String): RefreshToken?
    fun deleteByUserIdAndHashedToken(userId: String, hashedToken: String)
    fun save(refreshToken: RefreshToken)
}

interface UserRepository {
    fun findUserByEmail(email: String): User?
    fun findById(id: String): User?
    fun save(user: User): User
}