package com.os.kotlin_security.service.concrete

import com.os.kotlin_security.dto.request.auth.LoginRequest
import com.os.kotlin_security.dto.request.auth.RegisterRequest
import com.os.kotlin_security.dto.response.auth.LoginResponse
import com.os.kotlin_security.entity.User
import com.os.kotlin_security.mapper.Auth
import com.os.kotlin_security.security.BaseJwtService
import com.os.kotlin_security.service.AuthService
import com.os.kotlin_security.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.naming.AuthenticationException
import kotlin.collections.HashMap

@Service
class AuthServiceImpl(
    val passwordEncoder: PasswordEncoder,
    val userService: UserService,
    val authenticationManager: AuthenticationManager,
    val baseJwtService: BaseJwtService,

): AuthService {
    private val auth = Auth()


    override fun register(request: RegisterRequest) {
        val user = User(email = request.email, _password = request.password)

        user._password = passwordEncoder.encode(request.password)

        userService.add(user)
    }

    @Override
    override fun login(request: LoginRequest):LoginResponse {
        try {
            val authentication: Authentication = authenticationManager
                .authenticate(UsernamePasswordAuthenticationToken(request.email, request.password))

            val user: UserDetails = userService.loadUserByUsername(request.email)
            val userId: Int = (user as User).id!!

            val claims: MutableMap<String, Any> = HashMap()
            val roles: List<String> = user.authorities
                .map { it.authority }

            claims["roles"] = roles
            claims["userId"] = userId

            val token = baseJwtService.generateToken(request.email, claims as Map<String, Object>)
            println("token : "+ token)
            return LoginResponse(userId,token)

        } catch (e: BadCredentialsException) {
            throw e
        }
    }


}

