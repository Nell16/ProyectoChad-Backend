package com.proyectochad.backend.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Factura(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val fecha: LocalDateTime = LocalDateTime.now(),

    val total: Double,

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    val cliente: Usuario,

    @OneToOne
    @JoinColumn(name = "reparacion_id")
    val reparacion: Reparacion
)
