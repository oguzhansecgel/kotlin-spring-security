package com.os.kotlin_security.entity


import org.springframework.security.core.GrantedAuthority

enum class Role(private val roleName: String) : GrantedAuthority {

    ADMIN("ROLE_ADMIN"),
    RESIDENT("ROLE_RESIDENT");

    // This method returns the role name as authority
    override fun getAuthority(): String {
        return roleName
    }
}