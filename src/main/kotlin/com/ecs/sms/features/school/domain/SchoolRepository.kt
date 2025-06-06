package com.ecs.sms.features.school.domain

import com.ecs.sms.features.school.domain.models.*

// --- COURSES ---

interface CourseTypeRepository {
    fun findAll(): List<CourseType>
    fun findById(id: String): CourseType?
    fun save(courseType: CourseType): CourseType
    fun deleteById(id: String)
}

interface CourseInstanceRepository {
    fun findAll(): List<CourseInstance>
    fun findById(id: String): CourseInstance?
    fun save(instance: CourseInstance): CourseInstance
    fun deleteById(id: String)
}

// --- STUDENTS & ENROLLMENTS ---

interface StudentRepository {
    fun findAll(): List<Student>
    fun findById(id: String): Student?
    fun save(student: Student): Student
    fun deleteById(id: String)
}

interface EnrollmentRepository {
    fun findByCourseInstanceId(courseId: String): List<Enrollment>
    fun findByStudentId(studentId: String): List<Enrollment>
    fun save(enrollment: Enrollment): Enrollment
    fun delete(courseId: String, studentId: String)
}

// --- TEACHERS & AVAILABILITY ---

interface TeacherRepository {
    fun findAll(): List<Teacher>
    fun findById(id: String): Teacher?
    fun save(teacher: Teacher): Teacher
    fun deleteById(id: String)
}

// --- LESSONS ---

interface LessonRepository {
    fun findAll(): List<Lesson>
    fun findById(id: String): Lesson?
    fun findByCourseInstanceId(courseId: String): List<Lesson>
    fun save(lesson: Lesson): Lesson
    fun deleteById(id: String)
}