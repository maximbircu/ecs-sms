package com.ecs.sms.features.school.domain.models

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

        email?.let {
            require(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matches(it)) {
                "Invalid email format"
            }
        }
    }
}

class DuplicateStudentException(message: String) : RuntimeException(message)
