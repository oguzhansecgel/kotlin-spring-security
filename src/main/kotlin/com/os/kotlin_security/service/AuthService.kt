package com.os.kotlin_security.service

import com.os.kotlin_security.dto.request.auth.LoginRequest
import com.os.kotlin_security.dto.request.auth.RegisterRequest
import com.os.kotlin_security.dto.response.auth.LoginResponse

interface AuthService {
    fun register(request : RegisterRequest)
    fun login(request: LoginRequest):LoginResponse
}