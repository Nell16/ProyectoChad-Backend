package com.proyectochad.backend.model

import jakarta.persistence.*
import jakarta.validation.constraints.*

@Entity
data class Servicio(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank
    @field:Size(max = 100)
    val nombre: String,

    @field:NotBlank
    @field:Size(max = 255)
    val descripcion: String,

    @field:Positive
    val precioBase: Double
)
