package com.ecs.sms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EcsSmsApplication

fun main(args: Array<String>) {
	runApplication<EcsSmsApplication>(*args)
}
