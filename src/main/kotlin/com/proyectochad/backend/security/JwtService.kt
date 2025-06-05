package com.proyectochad.backend.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService {

    private val jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generarToken(correo: String, rol: String): String {
        val ahora = Date()
        val expiracion = Date(ahora.time + 1000 * 60 * 60) // 1 hora

        return Jwts.builder()
            .setSubject(correo)
            .claim("rol", rol)
            .setIssuedAt(ahora)
            .setExpiration(expiracion)
            .signWith(jwtSecret)
            .compact()
    }
}
