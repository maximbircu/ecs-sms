package com.ecs.sms.features.school.web.teacher

import com.ecs.sms.features.school.domain.services.TeacherService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/teacher")
class TeacherController(private val teacherService: TeacherService) {
    @GetMapping
    fun list(): List<TeacherResponse> {
        return teacherService.listTeachers().map { it.toResponse() }
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): ResponseEntity<TeacherResponse> {
        val teacher = teacherService.getTeacher(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(teacher.toResponse())
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        teacherService.deleteTeacher(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping
    fun create(@RequestBody request: CreateTeacherRequest): TeacherResponse {
        val teacher = teacherService.createTeacher(
            fullName = request.fullName,
            email = request.email,
            phone = request.phone,
            availability = request.availability
        )
        return teacher.toResponse()
    }
}