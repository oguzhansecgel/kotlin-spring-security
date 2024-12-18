package com.os.kotlin_security.controller

import com.os.kotlin_security.service.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    val userService: UserService
) {
}