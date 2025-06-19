package com.ecs.sms.features.school.data.enrolment

import com.ecs.sms.features.school.data.student.StudentEntity
import com.ecs.sms.features.school.data.student.toDomain
import com.ecs.sms.features.school.data.student.toEntity
import com.ecs.sms.features.school.domain.models.Enrollment
import com.ecs.sms.features.school.domain.models.PaymentStatus
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "enrolments")
class EnrollmentEntity(
    @Id
    @Column(nullable = false, updatable = false)
    val id: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    var student: StudentEntity? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    val paymentStatus: PaymentStatus = PaymentStatus.RESERVED,

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "course_id", nullable = false)
//    var course: CourseEntity? = null // Set by mapper when needed
)

/** Mappers */
fun Enrollment.toEntity(): EnrollmentEntity = EnrollmentEntity(
//    course = course.toEntity(),
    student = student.toEntity(),
    paymentStatus = paymentStatus
)

fun EnrollmentEntity.toDomain(): Enrollment = Enrollment(
//    course = course?.toDomain()!!,
    student = student?.toDomain()!!,
    paymentStatus = paymentStatus
)