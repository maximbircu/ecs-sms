package com.ecs.sms.features.auth.domain.models

data class AuthenticatedUser(
    val id: String,
    val email: String,
    val roles: List<Role> = emptyList()
) {
    val isAdmin: Boolean = roles.contains(Role.ADMIN)
}