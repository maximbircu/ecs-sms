package com.ecs.sms.features.school.web.course.models

import com.ecs.sms.core.domain.EnglishLevel
import com.ecs.sms.features.school.domain.models.Enrollment
import com.ecs.sms.features.school.domain.models.course.Course
import com.ecs.sms.features.school.domain.models.course.CourseSchedule
import com.ecs.sms.features.school.domain.models.course.CourseStatus
import com.ecs.sms.features.school.domain.models.course.CourseType
import com.ecs.sms.features.school.web.teacher.models.TeacherResponse
import com.ecs.sms.features.school.web.teacher.models.toResponse

data class CourseResponse(
    val id: String,
    val type: CourseType,
    val level: EnglishLevel,
    val schedule: CourseSchedule?,
    val teacher: TeacherResponse?,
    val status: CourseStatus,
    val enrolments: List<Enrollment>
)

fun Course.toResponse() : CourseResponse = CourseResponse(
    id = id,
    level = level,
    type = type,
    schedule = schedule,
    teacher = teacher?.toResponse(),
    status = status,
    enrolments = enrollments
)

