package com.ecs.sms.features.school.domain

import com.ecs.sms.features.school.domain.models.CourseInstance

interface CourseRepository {
    fun save(course: CourseInstance): CourseInstance
    fun findById(id: String): CourseInstance?
    fun findAll(): List<CourseInstance>
    fun deleteById(id: String)
}
