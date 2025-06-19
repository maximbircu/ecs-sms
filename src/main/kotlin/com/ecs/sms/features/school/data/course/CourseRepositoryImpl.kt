package com.ecs.sms.features.school.data.course

import com.ecs.sms.features.school.domain.CourseRepository
import com.ecs.sms.features.school.domain.models.course.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CourseRepositoryImpl(private val jpaRepository: CourseJpaRepository) : CourseRepository {
    override fun findById(id: String): Course? = jpaRepository.findByIdOrNull(id)?.toDomain()

    override fun save(course: Course): Course {
        println(course)

        return jpaRepository.save(course.toEntity()).toDomain()
    }

    override fun deleteById(id: String) {
        jpaRepository.deleteById(id)
    }

    override fun readAll(): List<Course> = jpaRepository.findAll().map { it.toDomain() }

    override fun findAllById(ids: Collection<String>): List<Course> {
        return jpaRepository.findAllById(ids).map { it.toDomain() }
    }
}

interface CourseJpaRepository : JpaRepository<CourseEntity, String>