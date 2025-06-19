package com.ecs.sms.features.school.domain.models

import com.ecs.sms.core.domain.EnglishLevel
import com.ecs.sms.core.domain.TimeRange
import com.ecs.sms.features.school.domain.models.CourseInstanceStatus.FORMING
import java.time.DayOfWeek
import java.time.LocalDate
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

data class CourseInstance(
    val id: String,
    val level: EnglishLevel,
    val type: CourseType,
    val schedule: CourseSchedule? = null,
    val teacherId: String? = null,
    val enrolments: List<Enrollment> = emptyList(),
    val status: CourseInstanceStatus = CourseInstanceStatus.DRAFT
) {
    init {
        require(id.isNotBlank()) { "Student ID cannot be blank" }
    }

    fun enroll(
        student: Student,
        paymentStatus: PaymentStatus
    ): Enrollment {
        if (teacherId == null) {
            throw IllegalStateException("Cannot enroll students before assigning a teacher.")
        }

        if (schedule == null) {
            throw IllegalStateException("Cannot enroll students before setting a schedule.")
        }

        if (status !in listOf(FORMING)) {
            throw IllegalStateException("Only FORMING courses can accept new enrollments.")
        }

        if (enrolments.any { it.studentId == student.id }) {
            throw IllegalStateException("Student is already enrolled.")
        }

        if (enrolments.size >= type.maxCapacity) {
            throw IllegalStateException("Course has reached maximum capacity.")
        }

        return Enrollment(
            courseInstanceId = id,
            studentId = student.id,
            paymentStatus = paymentStatus
        )
    }

}

enum class CourseInstanceStatus {
    DRAFT,       // Not ready for enrolments
    FORMING,     // Gathering students, not started yet
    AWAITING,    // Formed but waiting for start date
    STARTED,     // In progress
    FINISHED,    // All lessons completed
    CANCELLED    // Course was cancelled manually
}

data class CourseSchedule(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val weeklyTimeSlots: Map<DayOfWeek, TimeRange>
)

enum class CourseType(
    val label: String,
    val minCapacity: Int,
    val maxCapacity: Int,
    val lessonsCount: Int,
    val lessonDuration: Duration
) {
    GRP_6(
        label = "GRP-6",
        minCapacity = 6,
        maxCapacity = 10,
        lessonsCount = 24,
        lessonDuration = 90.minutes
    ),
    GRP_2(
        label = "GRP-2",
        minCapacity = 2,
        maxCapacity = 2,
        lessonsCount = 10,
        lessonDuration = 90.minutes
    ),
    IND(
        label = "IND",
        minCapacity = 1,
        maxCapacity = 1,
        lessonsCount = 10,
        lessonDuration = 60.minutes
    ),
    BAC_10(
        label = "BAC-10",
        minCapacity = 10,
        maxCapacity = 10,
        lessonsCount = 15,
        lessonDuration = 90.minutes
    ),
    BAC_IND(
        label = "BAC-IND",
        minCapacity = 1,
        maxCapacity = 1,
        lessonsCount = 10,
        lessonDuration = 60.minutes
    ),
    CAMBRIDGE(
        label = "CAMBRIDGE",
        minCapacity = 1,
        maxCapacity = 1,
        lessonsCount = 10,
        lessonDuration = 60.minutes
    ),
    IELTS(
        label = "IELTS",
        minCapacity = 1,
        maxCapacity = 1,
        lessonsCount = 10,
        lessonDuration = 60.minutes
    ),
    TOEFL(
        label = "TOEFL",
        minCapacity = 1,
        maxCapacity = 1,
        lessonsCount = 10,
        lessonDuration = 60.minutes
    ),
    CITIZENSHIP(
        label = "CITIZENSHIP",
        minCapacity = 1,
        maxCapacity = 1,
        lessonsCount = 10,
        lessonDuration = 60.minutes
    ),
    FDV_STANDARD(
        label = "FDV-Standard",
        minCapacity = 10,
        maxCapacity = 20,
        lessonsCount = 10,
        lessonDuration = 90.minutes
    ),
    FDV_PREMIUM(
        label = "FDV-Premium",
        minCapacity = 10,
        maxCapacity = 20,
        lessonsCount = 10,
        lessonDuration = 90.minutes
    ),
    FDV_VIP(
        label = "FDV-Vip",
        minCapacity = 10,
        maxCapacity = 20,
        lessonsCount = 10,
        lessonDuration = 90.minutes
    );

    companion object {
        fun findAll(): List<CourseType> = CourseType.entries

        fun findByLabel(label: String): CourseType? =
            CourseType.entries.find { it.label.equals(label, ignoreCase = true) }
    }
}