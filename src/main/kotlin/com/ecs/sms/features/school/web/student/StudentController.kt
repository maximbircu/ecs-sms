package com.ecs.sms.features.school.web.student

import com.ecs.sms.features.school.domain.services.StudentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/student")
class StudentController(
    private val studentService: StudentService
) {
    @PostMapping
    fun create(@RequestBody request: CreateStudentRequest): StudentResponse {
        val student = studentService.createStudent(
            fullName = request.fullName,
            email = request.email,
            phone = request.phone,
            crmId = request.crmId
        )
        return student.toResponse()
    }

    @GetMapping
    fun list(): List<StudentResponse> {
        return studentService.listStudents().map { it.toResponse() }
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): ResponseEntity<StudentResponse> {
        val student = studentService.getStudent(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(student.toResponse())
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        studentService.deleteStudent(id)
        return ResponseEntity.noContent().build()
    }
}