package com.ecs.sms.features.profile.web

import com.ecs.sms.features.auth.domain.models.AuthenticatedUser
import com.ecs.sms.features.auth.infrastructure.CurrentUser
import com.ecs.sms.features.profile.data.ProfilesRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profile")
class ProfileController(
    private val profilesRepository: ProfilesRepository
) {
    data class ProfileResponse(val name: String)

    @GetMapping("/me")
    fun getCurrentProfile(@CurrentUser user: AuthenticatedUser): ResponseEntity<ProfileResponse> {
        val profile = profilesRepository.getProfileById(user.id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(ProfileResponse(profile.name))
    }
}