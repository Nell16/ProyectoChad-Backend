package com.proyectochad.backend.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class AsignarServicioDTO(
    @field:NotNull(message = "El ID del servicio es obligatorio")
    @field:Min(1, message = "El ID del servicio debe ser mayor que 0")
    val servicioId: Long
)
