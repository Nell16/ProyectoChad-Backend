package com.proyectochad.backend.model

import jakarta.persistence.*

@Entity
data class Componente(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val cantidad: Int,

    @ManyToOne
    @JoinColumn(name = "reparacion_id", nullable = true)
    var reparacion: Reparacion? = null
)
