package com.ecs.sms.features.school.data.student

import com.ecs.sms.features.school.domain.StudentRepository
import com.ecs.sms.features.school.domain.models.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface StudentJpaRepository : JpaRepository<StudentEntity, String>

@Repository
class StudentRepositoryImpl(
    private val jpaRepository: StudentJpaRepository
) : StudentRepository {
    override fun findAll(): List<Student> {
        return jpaRepository.findAll().map { it.toDomain() }
    }

    override fun findById(id: String): Student? {
        return jpaRepository.findById(id).orElse(null)?.toDomain()
    }

    override fun save(student: Student): Student {
        val saved = jpaRepository.save(student.toEntity())
        return saved.toDomain()
    }
}
