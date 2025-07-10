package com.proyectochad.backend.dto

data class UsuarioDTO(
    val id: Long,
    val primerNombre: String,
    val segundoNombre: String?,
    val primerApellido: String,
    val segundoApellido: String?,
    val correo: String,
    val telefono: String,
    val fotoPerfilUrl: String?,
    val rol: String
)

