package com.ecs.sms.features.school.data.student

import com.ecs.sms.features.school.data.enrolment.EnrollmentEntity
import com.ecs.sms.features.school.domain.models.Student
import jakarta.persistence.*

@Entity
@Table(name = "students")
data class StudentEntity(
    @Id
    @Column(nullable = false, updatable = false)
    val id: String = "",

    @Column(name = "crm_id", nullable = false, unique = true)
    val crmId: String = "",

    @Column(name = "full_name", nullable = false)
    val fullName: String = "",

    @Column(name = "email", nullable = true)
    val email: String? = null,

    @Column(name = "phone", nullable = false)
    val phone: String = "",

    @OneToMany(mappedBy = "student", cascade = [CascadeType.ALL], orphanRemoval = true)
    val enrolments: MutableList<EnrollmentEntity> = mutableListOf()
)


/** Mappers */
fun Student.toEntity(): StudentEntity = StudentEntity(
    id = id,
    crmId = crmId,
    fullName = fullName,
    email = email,
    phone = phone
)

fun StudentEntity.toDomain(): Student = Student(
    id = id,
    crmId = crmId,
    fullName = fullName,
    email = email,
    phone = phone
)

