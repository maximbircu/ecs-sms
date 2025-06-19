package com.ecs.sms.features.auth.data.token

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.*

@Entity
@Table(name = "refresh_tokens")
class RefreshTokenEntity(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    val userId: String = "",

    @Column(nullable = false)
    val expiresAt: Instant = Instant.now(),

    @Column(nullable = false, unique = true)
    val hashedToken: String = "",

    @Column(nullable = false)
    val createdAt: Instant = Instant.now()
)
