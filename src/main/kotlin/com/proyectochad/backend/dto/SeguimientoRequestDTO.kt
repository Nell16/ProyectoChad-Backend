package com.proyectochad.backend.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class SeguimientoRequestDTO(

    @field:NotBlank(message = "La descripción del seguimiento es obligatoria")
    val descripcion: String,

    @field:NotNull(message = "El ID del autor es obligatorio")
    @field:Min(1, message = "El ID del autor debe ser mayor que 0")
    val autorId: Long,

    @field:NotNull(message = "El ID de la reparación es obligatorio")
    @field:Min(1, message = "El ID de la reparación debe ser mayor que 0")
    val reparacionId: Long
)
