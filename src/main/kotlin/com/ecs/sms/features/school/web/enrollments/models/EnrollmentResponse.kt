package com.ecs.sms.features.school.web.enrollments.models

import com.ecs.sms.features.school.domain.models.PaymentStatus

data class EnrollmentResponse(
    val studentId: String,
    val courseId: String,
    val status: PaymentStatus
)