package com.os.kotlin_security.security

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Service


@Service
class BaseSecurityService(
     private val baseJwtFilter: BaseJwtFilter
) {

    fun configureCommonSecurityRules(http: HttpSecurity)
    {
        http.csrf { obj: AbstractHttpConfigurer<*, *> -> obj.disable() } // Cross-Site Request Forgery
            .httpBasic { obj: AbstractHttpConfigurer<*, *> -> obj.disable() }
            .addFilterBefore(baseJwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}