package com.ecs.sms.features.school.web.course

import com.ecs.sms.core.domain.EnglishLevel
import com.ecs.sms.features.school.domain.models.CourseInstanceStatus
import com.ecs.sms.features.school.domain.models.CourseSchedule
import com.ecs.sms.features.school.domain.models.Enrollment

data class CourseResponse(
        val id: String,
        val level: EnglishLevel,
        val schedule: CourseSchedule?,
        val teacherId: String?,
        val status: CourseInstanceStatus,
        val enrolments: List<Enrollment>
)
