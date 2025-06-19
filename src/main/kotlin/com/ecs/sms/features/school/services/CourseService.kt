package com.ecs.sms.features.school.services

import com.ecs.sms.core.domain.EnglishLevel
import com.ecs.sms.features.school.domain.CourseRepository
import com.ecs.sms.features.school.domain.StudentRepository
import com.ecs.sms.features.school.domain.TeacherRepository
import com.ecs.sms.features.school.domain.models.Student
import com.ecs.sms.features.school.domain.models.Teacher
import com.ecs.sms.features.school.domain.models.course.Course
import com.ecs.sms.features.school.domain.models.course.CourseSchedule
import com.ecs.sms.features.school.domain.models.course.CourseStatus
import com.ecs.sms.features.school.domain.models.course.CourseType
import org.springframework.stereotype.Service

@Service
class CourseService(
    private val teacherRepository: TeacherRepository,
    private val courseRepository: CourseRepository,
    private val studentRepository: StudentRepository
) {
    fun create(
        level: EnglishLevel,
        type: CourseType,
        schedule: CourseSchedule?,
        teacherId: String?,
        status: CourseStatus = CourseStatus.DRAFT
    ): Course = courseRepository.save(
        Course(
            _level = level,
            _type = type,
            _schedule = schedule,
            _teacher = teacherId?.let { teacherRepository.findById(it) },
            _status = status
        )
    )

    fun enrollStudent(courseId: String, studentId: String) {
        val course = courseRepository.findById(courseId) ?: throw Course.notFound(courseId)
        val student = studentRepository.findById(studentId) ?: throw Student.notFound(studentId)
        course.enroll(student)
        courseRepository.save(course)
    }

    fun assignTeacher(courseId: String, teacherId: String) {
        val course = courseRepository.findById(courseId) ?: throw Course.notFound(courseId)
        val teacher = teacherRepository.findById(teacherId) ?: throw Teacher.notFound(teacherId)
        course.assignTeacher(teacher)
        courseRepository.save(course)
    }

    fun getAll(): List<Course> = courseRepository.readAll()
    fun getById(id: String): Course = courseRepository.findById(id) ?: throw Course.notFound(id)
}
