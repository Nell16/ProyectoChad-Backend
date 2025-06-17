package com.proyectochad.backend.dto

data class LoginResponseDTO(
    val token: String,
    val correo: String,
    val rol: String,
    val idUsuario: Long
)
