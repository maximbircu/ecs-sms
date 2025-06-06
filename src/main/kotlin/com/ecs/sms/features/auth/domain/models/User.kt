package com.ecs.sms.features.auth.domain.models

import java.util.*

data class User(
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    val hashedPassword: String,
    val roles: List<Role> = emptyList()
)