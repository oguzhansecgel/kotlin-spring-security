package com.os.kotlin_security.service.concrete

import com.os.kotlin_security.entity.User
import com.os.kotlin_security.repository.UserRepository
import com.os.kotlin_security.service.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class UserServiceImpl (
    private val userRepository: UserRepository
): UserService {
    override fun add(user: User?) {
       user?.let {
           userRepository.save(it)
       }
    }
    override fun loadUserByUsername(username: String?): UserDetails? {
        return userRepository.findByEmail(username)
            .orElseThrow { RuntimeException("User not found with email or password") }
    }

}