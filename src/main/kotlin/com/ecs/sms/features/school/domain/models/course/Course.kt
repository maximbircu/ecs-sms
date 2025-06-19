package com.ecs.sms.features.school.domain.models.course

import com.ecs.sms.core.domain.EnglishLevel
import com.ecs.sms.core.domain.error.ApiException
import com.ecs.sms.features.school.domain.models.Enrollment
import com.ecs.sms.features.school.domain.models.PaymentStatus
import com.ecs.sms.features.school.domain.models.Student
import com.ecs.sms.features.school.domain.models.Teacher
import java.util.*

data class Course(
    val id: String = UUID.randomUUID().toString(),
    private var _level: EnglishLevel,
    private var _type: CourseType,
    private var _schedule: CourseSchedule? = null,
    private var _teacher: Teacher? = null,
    private var _status: CourseStatus = CourseStatus.DRAFT,
    private val _enrollments: MutableList<Enrollment> = mutableListOf()
) {
    val level: EnglishLevel get() = _level
    val type: CourseType get() = _type
    val schedule: CourseSchedule? get() = _schedule
    val teacher: Teacher? get() = _teacher
    val status: CourseStatus get() = _status
    val enrollments: List<Enrollment> get() = _enrollments

    init {
        require(id.isNotBlank()) { "Course ID cannot be blank" }
    }

    fun enroll(student: Student) {
        if (_enrollments.any { it.student.id == student.id }) {
            throw studentAlreadyEnrolled()
        }
        if (_enrollments.size > _type.maxCapacity) {
            throw courseIsFull()
        }

        _enrollments.add(Enrollment(student = student, paymentStatus = PaymentStatus.RESERVED))
    }

    fun assignTeacher(teacher: Teacher) {
        _teacher = teacher
    }

    fun updateStatus(newStatus: CourseStatus) {
        _status = newStatus
    }

    fun updateSchedule(newSchedule: CourseSchedule) {
        _schedule = newSchedule
    }

    fun updateType(newType: CourseType) {
        _type = newType
    }

    fun updateLevel(newLevel: EnglishLevel) {
        _level = newLevel
    }

    companion object {
        fun notFound(id: String) = ApiException.notFound(
            code = "COURSE_NOT_FOUND",
            message = "Course $id not found"
        )

        fun studentAlreadyEnrolled() = ApiException.conflict(
            code = "STUDENT_ALREADY_ENROLLED",
            message = "Student already enrolled"
        )

        fun courseIsFull() = ApiException.invalid(
            code = "COURSE_IS_FULL",
            message = "Course is full"
        )
    }
}