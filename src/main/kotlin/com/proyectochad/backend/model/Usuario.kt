package com.proyectochad.backend.model

import jakarta.persistence.*

@Entity
data class Usuario(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val nombre: String,
    val correo: String,
    val contrasena: String,

    @Enumerated(EnumType.STRING)
    val rol: Rol = Rol.CLIENTE
)

enum class Rol {
    CLIENTE, TECNICO, ADMIN
}
