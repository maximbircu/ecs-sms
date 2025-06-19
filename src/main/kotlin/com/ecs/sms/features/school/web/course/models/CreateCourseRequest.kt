package com.ecs.sms.features.school.web.course.models

import com.ecs.sms.core.domain.EnglishLevel
import com.ecs.sms.features.school.domain.models.course.CourseSchedule
import com.ecs.sms.features.school.domain.models.course.CourseStatus
import com.ecs.sms.features.school.domain.models.course.CourseType

data class CreateCourseRequest(
    val level: EnglishLevel,
    val type: CourseType,
    val schedule: CourseSchedule? = null,
    val teacherId: String? = null,
    val status: CourseStatus = CourseStatus.DRAFT
)
