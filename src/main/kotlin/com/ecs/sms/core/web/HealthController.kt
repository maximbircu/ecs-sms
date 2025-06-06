package com.ecs.sms.core.web

import org.springframework.boot.info.BuildProperties
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthController(private val buildProperties: BuildProperties) {

    @GetMapping
    fun health(): ResponseEntity<String> {
        return ResponseEntity.ok("OK")
    }

    @GetMapping("/version")
    fun version(): Map<String, String> = mapOf(
        "version" to buildProperties.version,
        "timestamp" to buildProperties.time.toString()
    )
}