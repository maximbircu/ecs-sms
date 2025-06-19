package com.ecs.sms.core.domain.error

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

sealed class ApiException(
    val code: String,
    message: String?,
    val status: HttpStatus
) : RuntimeException(message) {
    companion object {
        fun notFound(code: String, message: String? = null) = NotFoundException(code, message)
        fun conflict(code: String, message: String? = null) = ConflictException(code, message)
        fun invalid(code: String, message: String? = null) = ValidationException(code, message)

        fun invalidEmailFormat() = invalid("INVALID_EMAIL_FORMAT", null)
    }
}

class NotFoundException(code: String, message: String?) : ApiException(code, message, NOT_FOUND)
class ConflictException(code: String, message: String?) : ApiException(code, message, CONFLICT)
class ValidationException(code: String, message: String?) : ApiException(code, message, BAD_REQUEST)