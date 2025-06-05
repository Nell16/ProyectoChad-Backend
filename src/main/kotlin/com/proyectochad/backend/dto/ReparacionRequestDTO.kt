package com.proyectochad.backend.dto

data class ReparacionRequestDTO(
    val usuarioId: Long,
    val tipoEquipo: String,
    val marca: String,
    val modelo: String,
    val descripcionFalla: String,
    val servicioId: Long
)
