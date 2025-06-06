package com.ecs.sms.features.auth.web

import com.ecs.sms.core.domain.ClientType
import com.ecs.sms.core.domain.ClientType.MOBILE
import com.ecs.sms.core.domain.ClientType.WEB
import com.ecs.sms.features.auth.domain.services.AuthData
import com.ecs.sms.features.auth.domain.services.AuthService
import com.ecs.sms.features.auth.web.models.LoginBody
import com.ecs.sms.features.auth.web.models.RegisterBody
import com.ecs.sms.features.auth.web.models.TokenRequestBody
import com.ecs.sms.features.auth.web.models.TokenResponseBody
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {
    init {
        register(RegisterBody("maximbircu@gmail.com", "qqqqqq"))
    }

    @PostMapping("/register")
    fun register(@RequestBody body: RegisterBody): ResponseEntity.BodyBuilder {
        authService.register(body.email, body.password)
        return ResponseEntity.ok()
    }

    @PostMapping("/login")
    fun login(
        @RequestHeader("Client-Type", required = false) rawClientType: String,
        @RequestBody body: LoginBody
    ): ResponseEntity<TokenResponseBody> {
        val clientType = ClientType.fromHeader(rawClientType)
        val authData = authService.login(body.email, body.password)
        return getResponse(clientType, authData)
    }

    @PostMapping("/logout")
    fun logout(
        @RequestHeader("Client-Type", required = false) rawClientType: String?,
    ): ResponseEntity<Void> {
        val clientType = ClientType.fromHeader(rawClientType)

        if (clientType == WEB) {
            val refreshToken = readRefreshTokenFromCookie()

            if (refreshToken != null) {
                authService.deleteRefreshToken(refreshToken) // 🔥 You should implement this
            }

            val deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build()

            return ResponseEntity
                .ok()
                .header("Set-Cookie", deleteCookie.toString())
                .build()
        }

        return ResponseEntity.ok().build()
    }


    @PostMapping("/token")
    fun token(
        @RequestHeader("Client-Type", required = false) rawClientType: String?,
        @RequestBody(required = false) body: TokenRequestBody?
    ): ResponseEntity<TokenResponseBody> {
        val clientType = ClientType.fromHeader(rawClientType)
        if (body?.refreshToken != null) {
            return getResponse(clientType, authService.refreshToken(body.refreshToken))
        }
        val refreshToken = readRefreshTokenFromCookie()
        if (refreshToken != null) {
            return getResponse(clientType, authService.refreshToken(refreshToken))
        }
        return ResponseEntity.badRequest().build()
    }


    private fun readRefreshTokenFromCookie(): String? {
        val cookies = (RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes)?.request?.cookies
        return cookies?.firstOrNull { it.name == "refreshToken" }?.value
    }

    private fun getResponse(clientType: ClientType, authData: AuthData): ResponseEntity<TokenResponseBody> {
        return when (clientType) {
            WEB -> {
                val refreshTokenCookie = ResponseCookie.from("refreshToken", authData.refreshToken)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(authService.getRefreshTokenValiditySeconds())
                    .build()

                ResponseEntity
                    .ok()
                    .header("Set-Cookie", refreshTokenCookie.toString())
                    .body(
                        TokenResponseBody(
                            accessToken = authData.accessToken,
                            userId = authData.user.id,
                            userEmail = authData.user.email
                        )
                    )
            }

            MOBILE -> ResponseEntity.ok(
                TokenResponseBody(
                    accessToken = authData.accessToken,
                    refreshToken = authData.refreshToken,
                    userId = authData.user.id,
                    userEmail = authData.user.email
                )
            )
        }
    }
}
