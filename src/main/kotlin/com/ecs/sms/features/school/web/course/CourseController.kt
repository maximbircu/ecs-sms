package com.ecs.sms.features.school.web.course

import com.ecs.sms.features.school.services.CourseService
import com.ecs.sms.features.school.web.course.models.CourseResponse
import com.ecs.sms.features.school.web.course.models.CreateCourseRequest
import com.ecs.sms.features.school.web.course.models.UpdateCourseTeacherRequest
import com.ecs.sms.features.school.web.course.models.toResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/course")
class CourseController(private val courseService: CourseService) {
    @PostMapping
    @Operation(summary = "Create a new course")
    fun create(@RequestBody request: CreateCourseRequest): CourseResponse {
        return courseService.create(
            level = request.level,
            type = request.type,
            schedule = request.schedule,
            teacherId = request.teacherId,
            status = request.status
        ).toResponse()
    }

    @GetMapping
    fun getAll(): List<CourseResponse> = courseService.getAll().map { it.toResponse() }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): CourseResponse {
        return courseService.getById(id).toResponse()
    }

    @PutMapping("/{id}/teacher")
    fun assignTeacher(
        @PathVariable id: String,
        @RequestBody request: UpdateCourseTeacherRequest
    ) {
        courseService.assignTeacher(courseId = id, teacherId = request.teacherId)
    }
}
