package com.ecs.sms.features.school.web.student

import com.ecs.sms.features.school.services.StudentService
import com.ecs.sms.features.school.web.student.models.CreateStudentRequest
import com.ecs.sms.features.school.web.student.models.StudentResponse
import com.ecs.sms.features.school.web.student.models.toResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/student")
class StudentController(
    private val studentService: StudentService
) {
    @PostMapping
    fun create(@RequestBody request: CreateStudentRequest): StudentResponse {
        return studentService.createStudent(
            fullName = request.fullName,
            email = request.email,
            phone = request.phone,
            crmId = request.crmId
        ).toResponse()
    }

    @GetMapping
    fun getAll(): List<StudentResponse> = studentService.listStudents().map { it.toResponse() }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): StudentResponse = studentService.getStudent(id).toResponse()
}