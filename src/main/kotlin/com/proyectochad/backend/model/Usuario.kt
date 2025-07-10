package com.proyectochad.backend.model

import jakarta.persistence.*
import jakarta.validation.constraints.*

@Entity
data class Usuario(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank
    @field:Size(max = 100)
    val primerNombre: String,

    val segundoNombre: String? = null,

    @field:NotBlank
    val primerApellido: String,

    val segundoApellido: String? = null,

    @field:NotBlank
    @field:Email
    val correo: String,

    @field:NotBlank
    @field:Size(min = 6, max = 100)
    val contrasena: String,

    @field:NotBlank
    val telefono: String,

    val fotoPerfilUrl: String? = null,

    @Enumerated(EnumType.STRING)
    @field:NotNull
    val rol: Rol = Rol.CLIENTE
)

enum class Rol {
    CLIENTE, TECNICO, ADMIN
}
