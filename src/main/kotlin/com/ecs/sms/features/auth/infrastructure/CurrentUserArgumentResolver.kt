package com.ecs.sms.features.auth.infrastructure

import com.ecs.sms.features.auth.domain.models.AuthenticatedUser
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class CurrentUserArgumentResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(CurrentUser::class.java) &&
                (parameter.parameterType == String::class.java ||
                        parameter.parameterType == AuthenticatedUser::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val auth = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("Missing authentication")

        return when (parameter.parameterType) {
            String::class.java -> {
                (auth.principal as? AuthenticatedUser)?.id
                    ?: throw IllegalStateException("Principal is not an AuthenticatedUser")
            }

            AuthenticatedUser::class.java -> {
                auth.principal as? AuthenticatedUser
                    ?: throw IllegalStateException("Principal is not an AuthenticatedUser")
            }

            else -> throw IllegalStateException("Unsupported @CurrentUser type: ${parameter.parameterType}")
        }
    }
}
