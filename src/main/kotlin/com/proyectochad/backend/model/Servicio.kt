package com.proyectochad.backend.model

import jakarta.persistence.*

@Entity
data class Servicio(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val nombre: String,
    val descripcion: String,
    val precioBase: Double
)