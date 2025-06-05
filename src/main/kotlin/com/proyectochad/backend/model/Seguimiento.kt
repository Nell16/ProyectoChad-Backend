package com.proyectochad.backend.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Seguimiento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val descripcion: String,

    val fecha: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "autor_id")
    val autor: Usuario,

    @ManyToOne
    @JoinColumn(name = "reparacion_id")
    val reparacion: Reparacion
)
