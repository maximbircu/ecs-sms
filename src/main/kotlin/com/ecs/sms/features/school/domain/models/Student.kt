package com.ecs.sms.features.school.domain.models

import com.ecs.sms.core.domain.error.ApiException
import com.ecs.sms.core.domain.error.ApiException.Companion.invalidEmailFormat

data class Student(
    val id: String,
    val crmId: String,
    val fullName: String,
    val email: String?,
    val phone: String
) {
    init {
        require(id.isNotBlank()) { "Student ID cannot be blank" }
        require(crmId.isNotBlank()) { "CRM ID cannot be blank" }
        require(fullName.isNotBlank()) { "Full name cannot be blank" }
        require(phone.isNotBlank()) { "Phone number cannot be blank" }
        email?.let(::validateEmail)
    }

    private fun validateEmail(email: String) {
        val isEmailValid = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matches(email)
        if (!isEmailValid) {
            throw invalidEmailFormat()
        }
    }

    companion object {
        fun notFound(id: String) = ApiException.notFound("STUDENT_NOT_FOUND", "Student $id not found")
        fun duplicateStudent() = ApiException.conflict("DUPLICATED_STUDENT", "Student already exists")
    }
}