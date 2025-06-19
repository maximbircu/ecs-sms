package com.ecs.sms.features.school.domain

import com.ecs.sms.features.school.domain.models.Enrollment
import com.ecs.sms.features.school.domain.models.Student
import com.ecs.sms.features.school.domain.models.Teacher
import com.ecs.sms.features.school.domain.models.course.Course

interface CourseRepository {
    fun readAll(): List<Course>
    fun findById(id: String): Course?
    fun findAllById(ids: Collection<String>): List<Course>
    fun save(course: Course): Course
    fun deleteById(id: String)
}

interface EnrollmentRepository {
    fun findById(id: Long): Enrollment?
    fun findByStudentId(studentId: String): List<Enrollment>
    fun save(enrollment: Enrollment): Enrollment
}

interface StudentRepository {
    fun findAll(): List<Student>
    fun findById(id: String): Student?
    fun save(student: Student): Student
}

interface TeacherRepository {
    fun findAll(): List<Teacher>
    fun findById(id: String): Teacher?
    fun save(teacher: Teacher): Teacher
    fun saveAll(teachers: List<Teacher>)
    fun deleteById(id: String)
}
