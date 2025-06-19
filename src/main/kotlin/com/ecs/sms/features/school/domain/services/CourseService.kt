package com.ecs.sms.features.school.domain.services

import com.ecs.sms.core.domain.EnglishLevel
import com.ecs.sms.features.school.domain.CourseRepository
import com.ecs.sms.features.school.domain.models.CourseInstance
import com.ecs.sms.features.school.domain.models.CourseInstanceStatus
import com.ecs.sms.features.school.domain.models.CourseSchedule
import com.ecs.sms.features.school.domain.models.CourseType
import org.springframework.stereotype.Service
import java.util.*

@Service
class CourseService(
    private val courseRepository: CourseRepository
) {
    fun createCourse(
        level: EnglishLevel,
        type: CourseType,
        schedule: CourseSchedule?,
        teacherId: String?,
        status: CourseInstanceStatus = CourseInstanceStatus.DRAFT
    ): CourseInstance {
        val course =
            CourseInstance(
                id = UUID.randomUUID().toString(),
                level = level,
                type = type,
                schedule = schedule,
                teacherId = teacherId,
                enrolments = emptyList(),
                status = status
            )
        return courseRepository.save(course)
    }

    fun listCourses(): List<CourseInstance> {
        return courseRepository.findAll()
    }

    fun getCourse(id: String): CourseInstance? {
        return courseRepository.findById(id)
    }
}
