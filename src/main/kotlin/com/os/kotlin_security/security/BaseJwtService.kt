package com.os.kotlin_security.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import javax.crypto.SecretKey


@Service
class BaseJwtService(
    private val EXPIRATION: Long = 6000000,
    private val SECRET_KEY : String = "cQis7hFG8pR/i4ZvnVUQrHQJ6oTm2wEcZa5F2r8IryZuqZX3OeEvhXQvVfwkhrxTyZEoUZhSTtetKQKDY2J6t6ArhgO7+0XRVFFM3dE2N2+FKTU3v4ulQBxj3r7s3me4FPXrjgOJPkfJdBFYfQFYl/m28zkS1xzj/V+ZljEJ5u1kZSNVb6ImMAmYqN5U0ULaVRZUXmRYDxRDLBZ/uanIuc7E0FJ0oICLHRaG8lXz0VdoSgpLJrKsYuJaGNVvGvI6odHJec9VQAOIn5Ay5WtmSTb0KLtL5I5MpKdm6P4UgcBfGfd7CY9JR2gNsHlAZH+t/LkwBA75os6UVAbQQu0RPjkJ54sRIlCalvCApuhX36g="
)
{
    internal fun generateToken(userName: String, extraClaims : Map<String,Object>):String
    {
        val token = Jwts
            .builder()
            .subject(userName)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + EXPIRATION))
            .claims(extraClaims)
            .signWith(getSigninKey())
            .compact()

        return token
    }
    fun validateToken(token: String): Boolean {
        return getTokenClaims(token).expiration.after(Date())
    }

    private fun getTokenClaims(token: String): Claims {
        // parser ise çözümlemek decode için kullanılır
        return Jwts.parser().verifyWith(getSigninKey() as SecretKey).build().parseSignedClaims(token).payload
    }

    fun extractUsername(token: String): String {
        return getTokenClaims(token).subject
    }

    fun extractRoles(token: String): List<String> {
        val roles: List<*> = getTokenClaims(token).get("roles", MutableList::class.java)
        return roles.filterIsInstance<String>()
    }

    fun extractUserId(token: String): Int {
        val userId = getTokenClaims(token)["userId"]
        return if (userId is Int) {
            userId as Int
        } else if (userId is String) {
            (userId as String).toInt()
        } else if (userId is Long) {
            (userId as Long).toInt()
        } else {
            throw IllegalArgumentException("Invalid userId type in token")
        }
    }
    private fun getSigninKey(): Key {
        val keyBytes = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }

}