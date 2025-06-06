package com.ecs.sms.features.school.domain.models

import com.ecs.sms.core.domain.EnglishLevel
import com.ecs.sms.core.domain.TimeRange
import java.time.DayOfWeek
import java.time.LocalDate
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

data class CourseInstance(
    val id: String,
    val level: EnglishLevel,
    val schedule: CourseSchedule,
    val teacherId: String,
    val enrolments: List<Enrollment>,
    val status: CourseInstanceStatus
)

enum class CourseInstanceStatus {
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