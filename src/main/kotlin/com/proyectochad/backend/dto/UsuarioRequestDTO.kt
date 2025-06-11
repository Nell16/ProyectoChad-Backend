package com.proyectochad.backend.dto

data class UsuarioRequestDTO(
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val rol: String
)
