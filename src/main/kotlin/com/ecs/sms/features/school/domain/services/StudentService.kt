package com.ecs.sms.features.school.domain.services

import com.ecs.sms.features.school.domain.StudentRepository
import com.ecs.sms.features.school.domain.models.DuplicateStudentException
import com.ecs.sms.features.school.domain.models.Student
import org.springframework.stereotype.Service
import java.util.*

@Service
class StudentService(private val studentRepository: StudentRepository) {
    fun createStudent(
        fullName: String,
        email: String?,
        phone: String,
        crmId: String
    ): Student {
        if (isDuplicateStudent(email, phone)) {
            throw DuplicateStudentException("A student with the same email or phone already exists.")
        }

        val student = Student(
            id = UUID.randomUUID().toString(),
            crmId = crmId,
            fullName = fullName,
            email = email,
            phone = phone
        )
        return studentRepository.save(student)
    }

    fun getStudent(id: String): Student? {
        return studentRepository.findById(id)
    }

    fun listStudents(): List<Student> {
        return studentRepository.findAll()
    }

    fun deleteStudent(id: String) {
        studentRepository.deleteById(id)
    }

    private fun isDuplicateStudent(email: String?, phone: String): Boolean {
        // Phone must always be unique
        val phoneExists = studentRepository.findAll().any { it.phone == phone }

        // Email is optional, but must be unique if provided
        val emailExists = email?.let { nonNullEmail ->
            studentRepository.findAll().any { it.email == nonNullEmail }
        } ?: false

        return phoneExists || emailExists
    }
}