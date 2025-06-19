package com.ecs.sms.features.school.domain.models

import com.ecs.sms.core.domain.TimeRange
import com.ecs.sms.core.domain.error.ApiException
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.springframework.stereotype.Component
import java.time.DayOfWeek

data class Teacher(
    val id: String,
    val fullName: String,
    val email: String?,
    val phone: String,
    val availability: TeacherAvailability
) {
    companion object {
        fun notFound(id: String) = ApiException.notFound("TEACHER_NOT_FOUND", "Teacher $id not found")
    }
}

data class TeacherAvailability(val availabilityMap: Map<DayOfWeek, List<AvailabilitySlot>>)
data class AvailabilitySlot(val timeRange: TimeRange, val type: AvailabilityType)

enum class AvailabilityType {
    AVAILABLE, // can teach
    BREAK, // short break
    OFF // not working
}

@Component
@Converter(autoApply = false)
class TeacherAvailabilityConverter(private val objectMapper: ObjectMapper) :
    AttributeConverter<TeacherAvailability, String> {

    override fun convertToDatabaseColumn(attribute: TeacherAvailability): String {
        return objectMapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String): TeacherAvailability {
        return objectMapper.readValue(dbData, TeacherAvailability::class.java)
    }
}