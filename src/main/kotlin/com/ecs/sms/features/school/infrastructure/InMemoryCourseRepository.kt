package com.ecs.sms.features.school.infrastructure

import com.ecs.sms.features.school.domain.CourseRepository
import com.ecs.sms.features.school.domain.models.CourseInstance
import java.util.concurrent.ConcurrentHashMap
import org.springframework.stereotype.Repository

@Repository
class InMemoryCourseRepository : CourseRepository {
    private val data = ConcurrentHashMap<String, CourseInstance>()

    override fun save(course: CourseInstance): CourseInstance {
        data[course.id] = course
        return course
    }

    override fun findById(id: String): CourseInstance? {
        return data[id]
    }

    override fun findAll(): List<CourseInstance> {
        return data.values.toList()
    }

    override fun deleteById(id: String) {
        data.remove(id)
    }
}
