package com.ecs.sms.core.domain.error

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(ApiException::class)
    fun handleApiException(ex: ApiException): ResponseEntity<ApiError> {
        return ResponseEntity.status(ex.status).body(ApiError(ex.code, ex.message))
    }


    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception): ResponseEntity<ApiError> {
        logger.error("Unhandled exception occurred", ex)

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ApiError("UNKNOWN_ERROR", ex.message)
        )
    }
}

data class ApiError(
    val code: String,
    val message: String? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
)