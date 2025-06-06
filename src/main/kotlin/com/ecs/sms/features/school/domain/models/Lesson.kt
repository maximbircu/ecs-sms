package com.ecs.sms.features.school.domain.models

import com.ecs.sms.core.domain.TimeRange
import java.time.LocalDate

data class Lesson(
    val id: String,
    val courseInstanceId: String,
    val teacherId: String,
    val date: LocalDate,
    val timeRange: TimeRange,
    val status: LessonStatus
)

enum class LessonStatus {
    COMPLETED,              // ✅ Happened and teacher should be paid
    CANCELLED_BY_STUDENT,   // ❌ Not paid — cancelled by anyone
    CANCELLED_BY_TEACHER,   // ❌ Not paid
    SICK_LEAVE,             // 🟡 Paid under special policy
    HOLIDAY                 // 🟡 School closed, unpaid or not scheduled
}