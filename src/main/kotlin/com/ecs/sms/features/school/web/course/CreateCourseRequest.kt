package com.ecs.sms.features.school.web.course

import com.ecs.sms.core.domain.EnglishLevel
import com.ecs.sms.features.school.domain.models.CourseInstanceStatus
import com.ecs.sms.features.school.domain.models.CourseSchedule
import com.ecs.sms.features.school.domain.models.CourseType

data class CreateCourseRequest(
        val level: EnglishLevel,
        val type: CourseType,
        val schedule: CourseSchedule? = null,
        val teacherId: String? = null,
        val status: CourseInstanceStatus = CourseInstanceStatus.DRAFT
)
