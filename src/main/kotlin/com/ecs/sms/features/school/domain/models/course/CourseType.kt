package com.ecs.sms.features.school.domain.models.course

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

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
    );

    companion object {
        fun findAll(): List<CourseType> = CourseType.entries

        fun findByLabel(label: String): CourseType? =
            CourseType.entries.find { it.label.equals(label, ignoreCase = true) }
    }
}

@Converter(autoApply = false)
class CourseTypeConverter : AttributeConverter<CourseType, String> {
    override fun convertToDatabaseColumn(attribute: CourseType?): String? {
        return attribute?.label
    }

    override fun convertToEntityAttribute(dbData: String?): CourseType? {
        return CourseType.entries.find { it.label == dbData }
    }
}