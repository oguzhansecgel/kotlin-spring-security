package com.os.kotlin_security.repository

import com.os.kotlin_security.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Integer> {
    fun findByEmail(email: String?): Optional<User?>?
}