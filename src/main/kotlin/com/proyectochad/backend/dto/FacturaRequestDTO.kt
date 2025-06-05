package com.proyectochad.backend.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero

data class FacturaRequestDTO(

    @field:NotNull(message = "El ID del cliente es obligatorio")
    @field:Min(1, message = "El ID del cliente debe ser mayor que 0")
    val clienteId: Long,

    @field:NotNull(message = "El ID de la reparación es obligatorio")
    @field:Min(1, message = "El ID de la reparación debe ser mayor que 0")
    val reparacionId: Long,

)
