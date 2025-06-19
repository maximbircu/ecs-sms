package com.ecs.sms.features.school.data.course

import com.ecs.sms.core.domain.EnglishLevel
import com.ecs.sms.core.domain.TimeRange
import com.ecs.sms.features.school.data.enrolment.EnrollmentEntity
import com.ecs.sms.features.school.data.enrolment.toDomain
import com.ecs.sms.features.school.data.enrolment.toEntity
import com.ecs.sms.features.school.data.teacher.TeacherEntity
import com.ecs.sms.features.school.data.teacher.toDomain
import com.ecs.sms.features.school.data.teacher.toEntity
import com.ecs.sms.features.school.domain.models.course.*
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.*
import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "courses")
data class CourseEntity(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Enumerated(EnumType.STRING)
    val level: EnglishLevel? = null,

    @Convert(converter = CourseTypeConverter::class)
    val type: CourseType? = null,

    @Embedded
    val schedule: CourseScheduleEmbeddable? = null,

    // Foreign key reference to teacher
    @Column(name = "teacher_id", nullable = true)
    val teacherId: String? = null,

    // Optional: mapped entity for convenience (read-only)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", insertable = false, updatable = false)
    val teacher: TeacherEntity? = null,

    @Enumerated(EnumType.STRING)
    val status: CourseStatus = CourseStatus.DRAFT,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "course_id") // FK on enrollment table
    val enrolments: MutableList<EnrollmentEntity> = mutableListOf()
)

@Embeddable
data class CourseScheduleEmbeddable(
    val startDate: LocalDate = LocalDate.MIN,
    val endDate: LocalDate = LocalDate.MIN,
    @Convert(converter = WeeklyTimeSlotsConverter::class)
    val weeklyTimeSlots: Map<DayOfWeek, TimeRange> = emptyMap()
)

@Component
@Converter(autoApply = false)
class WeeklyTimeSlotsConverter(
    private val objectMapper: ObjectMapper
) : AttributeConverter<Map<DayOfWeek, TimeRange>, String> {
    override fun convertToDatabaseColumn(attribute: Map<DayOfWeek, TimeRange>?): String? {
        return attribute?.mapKeys { it.key.name }  // serialize DayOfWeek as string
            ?.let { objectMapper.writeValueAsString(it) }
    }

    override fun convertToEntityAttribute(dbData: String?): Map<DayOfWeek, TimeRange>? {
        if (dbData.isNullOrBlank()) return null

        val type = object : TypeReference<Map<String, TimeRange>>() {}
        val rawMap = objectMapper.readValue(dbData, type)

        return rawMap.mapKeys { DayOfWeek.valueOf(it.key) }
    }
}

/** Mappers */
fun Course.toEntity(): CourseEntity {
    return CourseEntity(
        id = id,
        level = level,
        type = type,
        status = status,
        teacherId = teacher?.id,
        teacher = teacher?.toEntity(),
        schedule = schedule?.toEmbeddable(),
        enrolments = enrollments.map { it.toEntity() }.toMutableList()
    )
}

fun CourseEntity.toDomain(): Course = Course(
    id = id,
    _level = level!!,
    _type = type!!,
    _status = status,
    _teacher = teacher?.toDomain(),
    _schedule = schedule?.toDomain(),
    _enrollments = enrolments.map { it.toDomain() }.toMutableList()
)

private fun CourseSchedule.toEmbeddable(): CourseScheduleEmbeddable = CourseScheduleEmbeddable(
    startDate = startDate,
    endDate = endDate,
    weeklyTimeSlots = weeklyTimeSlots
)

private fun CourseScheduleEmbeddable.toDomain(): CourseSchedule = CourseSchedule(
    startDate = startDate,
    endDate = endDate,
    weeklyTimeSlots = weeklyTimeSlots
)
