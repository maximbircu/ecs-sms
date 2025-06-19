package com.ecs.sms.features.school.domain.models.course

enum class CourseStatus {
    DRAFT,       // Not ready for enrolments
    FORMING,     // Gathering students, not started yet
    AWAITING,    // Formed but waiting for start date
    STARTED,     // In progress
    FINISHED,    // All lessons completed
    CANCELLED    // Course was cancelled manually
}