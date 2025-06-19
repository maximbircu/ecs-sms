package com.ecs.sms.features.school.web.teacher

import com.ecs.sms.features.school.domain.models.TeacherAvailability

data class CreateTeacherRequest(
    val fullName: String,
    val email: String?,
    val phone: String,
    val availability: TeacherAvailability
)
