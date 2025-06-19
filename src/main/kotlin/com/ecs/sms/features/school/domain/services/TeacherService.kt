package com.ecs.sms.features.school.domain.services

import com.ecs.sms.features.school.domain.TeacherRepository
import com.ecs.sms.features.school.domain.models.Teacher
import com.ecs.sms.features.school.domain.models.TeacherAvailability
import org.springframework.stereotype.Service
import java.util.*

@Service
class TeacherService(private val teacherRepository: TeacherRepository) {

    fun getTeacher(id: String): Teacher? {
        return teacherRepository.findById(id)
    }

    fun listTeachers(): List<Teacher> {
        return teacherRepository.findAll()
    }

    fun deleteTeacher(id: String) {
        teacherRepository.deleteById(id)
    }

    fun createTeacher(
        fullName: String,
        email: String?,
        phone: String,
        availability: TeacherAvailability
    ): Teacher {
        val teacher = Teacher(
            id = UUID.randomUUID().toString(),
            fullName = fullName,
            email = email,
            phone = phone,
            availability = availability
        )
        return teacherRepository.save(teacher)
    }
}
