package com.proyectochad.backend.dto

data class UsuarioDTO(
    val id: Long,
    val nombre: String,
    val correo: String,
    val rol: String
)
