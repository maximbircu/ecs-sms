package com.ecs.sms.features.school.data.teacher

import com.ecs.sms.features.school.domain.models.Teacher
import com.ecs.sms.features.school.domain.models.TeacherAvailability
import com.ecs.sms.features.school.domain.models.TeacherAvailabilityConverter
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "teachers")
data class TeacherEntity(
    @Id
    @Column(nullable = false, updatable = false)
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    val fullName: String = "",

    @Column(nullable = true)
    val email: String? = null,

    @Column(nullable = false)
    val phone: String = "",

    @Column(columnDefinition = "TEXT")
    @Convert(converter = TeacherAvailabilityConverter::class)
    var availability: TeacherAvailability = TeacherAvailability(emptyMap())
)

/** Mappers */
fun TeacherEntity.toDomain(): Teacher = Teacher(id, fullName, email, phone, availability)

fun Teacher.toEntity(): TeacherEntity = TeacherEntity(id, fullName, email, phone, availability)
