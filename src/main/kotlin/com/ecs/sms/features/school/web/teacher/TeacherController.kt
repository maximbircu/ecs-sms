package com.ecs.sms.features.school.web.teacher

import com.ecs.sms.features.school.services.TeacherService
import com.ecs.sms.features.school.web.teacher.models.CreateTeacherRequest
import com.ecs.sms.features.school.web.teacher.models.TeacherResponse
import com.ecs.sms.features.school.web.teacher.models.toResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/teacher")
class TeacherController(private val teacherService: TeacherService) {
    @PostMapping
    fun create(@RequestBody request: CreateTeacherRequest): TeacherResponse {
        return teacherService.createTeacher(
            fullName = request.fullName,
            email = request.email,
            phone = request.phone,
            availability = request.availability
        ).toResponse()
    }

    @GetMapping
    fun getAll(): List<TeacherResponse> = teacherService.listTeachers().map { it.toResponse() }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): TeacherResponse {
        return teacherService.getTeacher(id).toResponse()
    }
}