package com.proyectochad.backend.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ReparacionRequestDTO(

    @field:NotNull(message = "El ID de usuario es obligatorio")
    @field:Min(1, message = "El ID de usuario debe ser mayor que 0")
    val usuarioId: Long,

    @field:NotBlank(message = "El tipo de equipo es obligatorio")
    val tipoEquipo: String,

    @field:NotBlank(message = "La marca es obligatoria")
    val marca: String,

    @field:NotBlank(message = "El modelo es obligatorio")
    val modelo: String,

    @field:NotBlank(message = "La descripción de la falla es obligatoria")
    val descripcionFalla: String,

    // Servicio ahora es opcional
    val servicioId: Long? = null
)
