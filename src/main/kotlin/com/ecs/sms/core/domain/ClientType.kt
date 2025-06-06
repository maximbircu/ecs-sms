package com.ecs.sms.core.domain

enum class ClientType {
    WEB, MOBILE;

    companion object {
        fun fromHeader(value: String?): ClientType =
            when (value?.lowercase()) {
                "web" -> WEB
                "mobile" -> MOBILE
                else -> MOBILE // default fallback
            }
    }
}