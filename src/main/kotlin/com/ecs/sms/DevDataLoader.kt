package com.ecs.sms

import com.ecs.sms.features.school.data.course.CourseEntity
import com.ecs.sms.features.school.data.course.CourseJpaRepository
import com.ecs.sms.features.school.data.student.StudentEntity
import com.ecs.sms.features.school.data.student.StudentJpaRepository
import com.ecs.sms.features.school.data.teacher.TeacherEntity
import com.ecs.sms.features.school.data.teacher.TeacherJpaRepository
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("dev")
class DevDataLoader(
    private val objectMapper: ObjectMapper,
    private val teacherJpaRepository: TeacherJpaRepository,
    private val studentsJpaRepository: StudentJpaRepository,
    private val courseJpaRepository: CourseJpaRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        // Load fake teachers
        val teachers: List<TeacherEntity> = loadDataFromFile("/fixtures/teachers.json")
        teacherJpaRepository.saveAll(teachers)

        // Load fake students
        val students: List<StudentEntity> = loadDataFromFile("/fixtures/students.json")
        studentsJpaRepository.saveAll(students)

        // Load fake courses
        val courses: List<CourseEntity> = loadDataFromFile("/fixtures/courses.json")
        courseJpaRepository.saveAll(courses)

        // Load fake enrolments
    }

    private inline fun <reified T> loadDataFromFile(filePath: String): T {
        val resource = javaClass.getResource(filePath)!!
        return objectMapper.readValue(resource, object : TypeReference<T>() {})
    }
}