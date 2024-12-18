package com.os.kotlin_security.config

import com.os.kotlin_security.security.BaseSecurityService
import com.os.kotlin_security.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
internal class SecurityConfig(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val baseSecurityService: BaseSecurityService
) {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        baseSecurityService.configureCommonSecurityRules(http)
        http
            .authorizeHttpRequests  { authorizeRequests ->
                authorizeRequests
                    .anyRequest().permitAll()
            }// Ensure all other requests require authentication
        return http.build()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val daoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder)
        daoAuthenticationProvider.setUserDetailsService(userService)
        return daoAuthenticationProvider
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager {
        return configuration.authenticationManager
    }

    companion object {
        private val WHITE_LIST = arrayOf(
            "/api/auth/**",
            // Add other paths that should be publicly accessible
        )
    }
}