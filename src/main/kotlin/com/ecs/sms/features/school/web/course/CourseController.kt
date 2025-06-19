package com.ecs.sms.features.school.web.course

import com.ecs.sms.features.school.domain.services.CourseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/course")
class CourseController(private val courseService: CourseService) {
    @PostMapping
    fun create(@RequestBody request: CreateCourseRequest): CourseResponse {
        val course = courseService.createCourse(
            level = request.level,
            type = request.type,
            schedule = request.schedule,
            teacherId = request.teacherId,
            status = request.status
        )
        return CourseResponse(
            id = course.id,
            level = course.level,
            schedule = course.schedule,
            teacherId = course.teacherId,
            status = course.status,
            enrolments = course.enrolments
        )
    }

    @GetMapping
    fun list(): List<CourseResponse> {
        return courseService.listCourses().map {
            CourseResponse(
                id = it.id,
                level = it.level,
                schedule = it.schedule,
                teacherId = it.teacherId,
                status = it.status,
                enrolments = it.enrolments
            )
        }
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): ResponseEntity<CourseResponse> {
        val course = courseService.getCourse(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(
            CourseResponse(
                id = course.id,
                level = course.level,
                schedule = course.schedule,
                teacherId = course.teacherId,
                status = course.status,
                enrolments = course.enrolments
            )
        )
    }
}
