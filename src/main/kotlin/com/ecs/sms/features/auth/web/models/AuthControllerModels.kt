package com.ecs.sms.features.auth.web.models

data class RegisterBody(
    val email: String,
    val password: String
)

data class LoginBody(
    val email: String,
    val password: String
)

data class TokenResponseBody(
    val accessToken: String,
    val refreshToken: String? = null,
    val userId: String,
    val userEmail: String,
)

data class TokenRequestBody(
    val refreshToken: String,
)