package com.ecs.sms.features.auth.web

import com.ecs.sms.features.auth.domain.models.AuthenticatedUser
import com.ecs.sms.features.auth.infrastructure.CurrentUser
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController {
    data class UserResponse(
        val id: String,
        val email: String
    )

    @GetMapping
    fun getCurrentUser(@CurrentUser user: AuthenticatedUser): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(UserResponse(user.id, user.email))
    }
}