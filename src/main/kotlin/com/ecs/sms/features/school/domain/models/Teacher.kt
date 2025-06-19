package com.ecs.sms.features.school.domain.models

import com.ecs.sms.core.domain.TimeRange
import java.time.DayOfWeek

data class Teacher(
    val id: String,
    val fullName: String,
    val email: String?,
    val phone: String,
    val availability: TeacherAvailability
)

data class TeacherAvailability(val availabilityMap: Map<DayOfWeek, List<AvailabilitySlot>>)

data class AvailabilitySlot(val timeRange: TimeRange, val type: AvailabilityType)

enum class AvailabilityType {
    AVAILABLE, // can teach
    BREAK, // short break
    OFF // not working
}
