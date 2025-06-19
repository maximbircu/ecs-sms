package com.ecs.sms.features.school.web.teacher

import com.ecs.sms.features.school.domain.models.Teacher
import com.ecs.sms.features.school.domain.models.TeacherAvailability

data class TeacherResponse(
    val id: String,
    val fullName: String,
    val email: String?,
    val phone: String,
    val availability: TeacherAvailability
)

fun Teacher.toResponse(): TeacherResponse {
    return TeacherResponse(
        id = this.id,
        fullName = this.fullName,
        email = this.email,
        phone = this.phone,
        availability = availability
    )
}
