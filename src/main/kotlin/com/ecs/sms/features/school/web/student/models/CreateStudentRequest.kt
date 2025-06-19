package com.ecs.sms.features.school.web.student.models

import com.ecs.sms.features.school.domain.models.Student

data class CreateStudentRequest(
    val crmId: String,
    val fullName: String,
    val email: String?,
    val phone: String
)

data class StudentResponse(
    val id: String,
    val crmId: String,
    val fullName: String,
    val email: String?,
    val phone: String?
)

fun Student.toResponse() = StudentResponse(
    id = id,
    crmId = crmId,
    fullName = fullName,
    email = email,
    phone = phone
)