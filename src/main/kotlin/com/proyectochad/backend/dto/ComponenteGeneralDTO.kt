package com.proyectochad.backend.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class ComponenteGeneralDTO(
    @field:NotBlank(message = "El nombre del componente es obligatorio")
    val nombre: String,

    @field:NotBlank(message = "La descripción es obligatoria")
    val descripcion: String,

    @field:NotNull(message = "El precio es obligatorio")
    @field:Positive(message = "El precio debe ser mayor que 0")
    val precio: Double,

    @field:NotNull(message = "La cantidad es obligatoria")
    @field:Min(1, message = "La cantidad debe ser al menos 1")
    val cantidad: Int
)


