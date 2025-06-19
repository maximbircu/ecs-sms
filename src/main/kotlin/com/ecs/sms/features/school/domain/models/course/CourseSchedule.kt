package com.ecs.sms.features.school.domain.models.course

import com.ecs.sms.core.domain.TimeRange
import java.time.DayOfWeek
import java.time.LocalDate

data class CourseSchedule(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val weeklyTimeSlots: Map<DayOfWeek, TimeRange>
)