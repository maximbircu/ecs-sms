package com.ecs.sms.features.school.web.enrollments.models

data class EnrollStudentRequest(
    val courseId: String,
    val studentId: String
)