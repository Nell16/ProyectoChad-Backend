package com.proyectochad.backend.dto

data class LoginResponseDTO(
    val token: String,
    val idUsuario: Long,
    val correo: String,
    val rol: String,
    val primerNombre: String,
    val segundoNombre: String?,
    val primerApellido: String,
    val segundoApellido: String?,
    val telefono: String,
    val fotoPerfilUrl: String?
)

