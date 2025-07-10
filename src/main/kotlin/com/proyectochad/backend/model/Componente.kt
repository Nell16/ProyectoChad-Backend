package com.proyectochad.backend.model

import jakarta.persistence.*
import jakarta.validation.constraints.*

@Entity
data class Componente(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank(message = "El nombre no puede estar vacío")
    @field:Size(max = 100, message = "El nombre no debe exceder 100 caracteres")
    val nombre: String,

    @field:NotBlank(message = "La descripción no puede estar vacía")
    @field:Size(max = 255, message = "La descripción no debe exceder 255 caracteres")
    val descripcion: String,

    @field:Positive(message = "El precio debe ser mayor que 0")
    val precio: Double,

    @field:PositiveOrZero(message = "La cantidad no puede ser negativa")
    val cantidad: Int,

    @ManyToOne
    @JoinColumn(name = "reparacion_id", nullable = true)
    var reparacion: Reparacion? = null
)
