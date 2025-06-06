package com.ecs.sms.features.auth.domain.models

import java.time.Instant
import java.util.*

data class RefreshToken(
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val expiresAt: Instant,
    val hashedToken: String,
    val createdAt: Instant = Instant.now(),
)