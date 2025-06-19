package com.ecs.sms.core.domain

import java.time.LocalDate
import java.time.LocalTime

data class TimeRange(
    val start: LocalTime,
    val end: LocalTime
)

data class DateRange(
    val start: LocalDate,
    val end: LocalDate
)