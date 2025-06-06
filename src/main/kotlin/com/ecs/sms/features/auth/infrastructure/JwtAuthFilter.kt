package com.ecs.sms.features.auth.infrastructure

import com.ecs.sms.features.auth.domain.models.AuthenticatedUser
import com.ecs.sms.features.auth.domain.services.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val tokenService: TokenService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            if (tokenService.validateAccessToken(authHeader)) {
                val claims = tokenService.getClaimsFromToken(authHeader)
                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                    AuthenticatedUser(claims.userId, claims.email, claims.roles),
                    null,
                    claims.roles.map { SimpleGrantedAuthority("ROLE_${it.name}") }
                )
            }
        }
        filterChain.doFilter(request, response)
    }
}