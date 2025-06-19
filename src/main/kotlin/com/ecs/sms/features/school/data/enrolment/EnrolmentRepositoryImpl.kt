package com.ecs.sms.features.school.data.enrolment

import com.ecs.sms.features.school.domain.EnrollmentRepository
import com.ecs.sms.features.school.domain.models.Enrollment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class EnrolmentRepositoryImpl(private val jpaRepository: JpaEnrollmentRepository) : EnrollmentRepository {
    override fun findById(id: Long): Enrollment? = jpaRepository.findByIdOrNull(id)?.toDomain()

    override fun findByStudentId(studentId: String): List<Enrollment> {
        return jpaRepository.findByStudentId(studentId).map { it.toDomain() }
    }

    override fun save(enrollment: Enrollment): Enrollment {
        return jpaRepository.save(enrollment.toEntity()).toDomain()
    }
}

interface JpaEnrollmentRepository : JpaRepository<EnrollmentEntity, Long> {
    fun findByStudentId(studentId: String): List<EnrollmentEntity>
}