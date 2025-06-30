package com.proyectochad.backend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class ServicioRequestDTO(

    @field:NotBlank(message = "El nombre del servicio es obligatorio")
    val nombre: String,

    @field:NotBlank(message = "La descripción del servicio es obligatoria")
    val descripcion: String,

    @field:NotNull(message = "El precio base es obligatorio")
    @field:Positive(message = "El precio debe ser mayor que 0")
    val precioBase: Double
)
