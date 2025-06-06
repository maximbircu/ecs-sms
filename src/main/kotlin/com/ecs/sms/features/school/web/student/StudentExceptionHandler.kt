package com.ecs.sms.features.school.web.student

import com.ecs.sms.features.school.domain.models.DuplicateStudentException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(assignableTypes = [StudentController::class])
class StudentExceptionHandler {

    @ExceptionHandler(DuplicateStudentException::class)
    fun handleConflict(ex: DuplicateStudentException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.message)
    }
}