package com.ecs.sms.core.domain

import java.time.DayOfWeek
import java.time.LocalTime

data class WeeklySlot(
    val dayOfWeek: DayOfWeek,
    val timeRange: TimeRange
)

data class TimeRange(
    val start: LocalTime,
    val end: LocalTime
)