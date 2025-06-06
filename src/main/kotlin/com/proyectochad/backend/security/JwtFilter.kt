package com.proyectochad.backend.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import java.util.*

@Component
class JwtFilter : GenericFilterBean() {

    private val jwtSecret = Keys.hmacShaKeyFor("UnaClaveSecretaMuySeguraYDe32Bytes!!".toByteArray())

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val authHeader = httpRequest.getHeader("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)

            try {
                val claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .body

                val correo = claims.subject
                val rol = claims["rol"] as String

                val authorities = listOf(SimpleGrantedAuthority("ROLE_${rol.uppercase()}"))
                val userDetails = User(correo, "", authorities)

                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(httpRequest)

                SecurityContextHolder.getContext().authentication = authentication
            } catch (ex: Exception) {
                println("Token inválido: ${ex.message}")
            }
        }

        chain.doFilter(request, response)
    }
}
