package com.proyectochad.backend.dto

data class ComponenteRequestDTO(
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val cantidad: Int,
    val reparacionId: Long
)
