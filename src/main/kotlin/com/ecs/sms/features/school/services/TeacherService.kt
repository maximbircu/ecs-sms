package com.ecs.sms.features.school.services

import com.ecs.sms.features.school.domain.TeacherRepository
import com.ecs.sms.features.school.domain.models.Teacher
import com.ecs.sms.features.school.domain.models.TeacherAvailability
import org.springframework.stereotype.Service
import java.util.*

@Service
class TeacherService(private val teacherRepository: TeacherRepository) {
    fun createTeacher(
        fullName: String,
        email: String?,
        phone: String,
        availability: TeacherAvailability
    ): Teacher = teacherRepository.save(
        Teacher(
            id = UUID.randomUUID().toString(),
            fullName = fullName,
            email = email,
            phone = phone,
            availability = availability
        )
    )

    fun getTeacher(id: String): Teacher = teacherRepository.findById(id) ?: throw Teacher.notFound(id)

    fun listTeachers(): List<Teacher> = teacherRepository.findAll()
}