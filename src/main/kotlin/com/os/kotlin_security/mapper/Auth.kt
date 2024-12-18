package com.os.kotlin_security.mapper

import com.os.kotlin_security.dto.request.auth.LoginRequest
import com.os.kotlin_security.dto.request.auth.RegisterRequest
import com.os.kotlin_security.entity.User

class Auth {
    fun mapRegisterToDTO(user: User): RegisterRequest {
        return RegisterRequest(
            email = user.email,
            password = user._password,
        )
    }
    fun mapLoginToDTO(user: User): LoginRequest {
        return LoginRequest(
            email = user.email,
            password = user._password,
        )
    }
}