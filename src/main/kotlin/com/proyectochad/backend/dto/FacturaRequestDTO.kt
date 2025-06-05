package com.proyectochad.backend.dto

data class FacturaRequestDTO(
    val clienteId: Long,
    val reparacionId: Long,
    val total: Double
)
