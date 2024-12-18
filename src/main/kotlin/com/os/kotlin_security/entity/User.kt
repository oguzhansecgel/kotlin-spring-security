package com.os.kotlin_security.entity

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

import org.springframework.security.core.userdetails.UserDetails
import java.util.*
@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,
    var email: String,
    var _password: String,
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role_name")
    val roles: Set<String> = emptySet()

) : UserDetails {

    override fun getPassword(): String {
        return _password
    }

    override fun getUsername(): String {
        return email
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it) }
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}