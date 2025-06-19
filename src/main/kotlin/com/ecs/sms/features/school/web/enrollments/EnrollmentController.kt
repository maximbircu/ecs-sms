package com.ecs.sms.features.school.web.enrollments

import com.ecs.sms.features.school.services.CourseService
import com.ecs.sms.features.school.web.enrollments.models.EnrollStudentRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/enrollments")
class EnrollmentController(private val courseService: CourseService) {
    @PostMapping
    fun enrollStudent(@RequestBody request: EnrollStudentRequest): ResponseEntity<Void> {
        courseService.enrollStudent(
            courseId = request.courseId,
            studentId = request.studentId,
        )
        return ResponseEntity.ok().build()
    }
}