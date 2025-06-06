package com.ecs.sms.features.profile.data

import com.ecs.sms.features.profile.domain.Profile
import org.springframework.stereotype.Repository

@Repository
class ProfilesRepository {
    private val profiles: MutableList<Profile> = mutableListOf()

    fun getProfileById(userId: String): Profile? {
        return profiles.firstOrNull { it.userId == userId }
    }

    fun save(profile: Profile) {
        profiles.add(profile)
    }
}