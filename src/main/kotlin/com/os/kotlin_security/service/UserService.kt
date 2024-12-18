package com.os.kotlin_security.service

import com.os.kotlin_security.entity.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService: UserDetailsService {
    fun add(user: User?)
}