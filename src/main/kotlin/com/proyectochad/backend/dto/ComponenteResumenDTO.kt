// ComponenteResumenDTO.kt
package com.proyectochad.backend.dto

data class ComponenteResumenDTO(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val cantidad: Int
)
