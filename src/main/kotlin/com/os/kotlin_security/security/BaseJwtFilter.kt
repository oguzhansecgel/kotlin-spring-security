package com.os.kotlin_security.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class BaseJwtFilter
    (
    private val jwtService: BaseJwtService
            ): OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwtHeader = request.getHeader("Authorization")

        if (jwtHeader != null && jwtHeader.startsWith("Bearer ")) {
            val jwt = jwtHeader.substring(7)

            if (jwtService.validateToken(jwt)) {
                val username = jwtService.extractUsername(jwt)

                val roles = jwtService.extractRoles(jwt)

                val authorities = roles
                    .stream()
                    .map { role: String? ->
                        SimpleGrantedAuthority(
                            role
                        )
                    }
                    .toList()

                val token = UsernamePasswordAuthenticationToken(username, null, authorities)
                token.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = token
            }
        }
        filterChain.doFilter(request, response);
    }
}
