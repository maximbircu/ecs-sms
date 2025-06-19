package com.ecs.sms.features.school.data.teacher

import com.ecs.sms.features.school.domain.TeacherRepository
import com.ecs.sms.features.school.domain.models.Teacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface TeacherJpaRepository : JpaRepository<TeacherEntity, String>

@Repository
class TeacherRepositoryImpl(
    private val jpaRepository: TeacherJpaRepository
) : TeacherRepository {

    override fun findAll(): List<Teacher> {
        return jpaRepository.findAll().map { it.toDomain() }
    }

    override fun findById(id: String): Teacher? {
        return jpaRepository.findById(id).orElse(null)?.toDomain()
    }

    override fun save(teacher: Teacher): Teacher {
        return jpaRepository.save(teacher.toEntity()).toDomain()
    }

    override fun saveAll(teachers: List<Teacher>) {
        jpaRepository.saveAll(teachers.map { it.toEntity() })
    }

    override fun deleteById(id: String) {
        jpaRepository.deleteById(id)
    }
}
