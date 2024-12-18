package com.os.kotlin_security.controller

import com.os.kotlin_security.dto.request.auth.LoginRequest
import com.os.kotlin_security.dto.request.auth.RegisterRequest
import com.os.kotlin_security.dto.response.auth.LoginResponse
import com.os.kotlin_security.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    val authService: AuthService
) {


    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest)
    {
        authService.register(request)
    }
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest) : LoginResponse
    {
        return authService.login(request)
    }
}