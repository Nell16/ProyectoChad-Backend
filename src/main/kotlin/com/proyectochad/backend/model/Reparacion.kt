package com.proyectochad.backend.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Reparacion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    val usuario: Usuario,

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    val tecnico: Usuario? = null,

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    val servicio: Servicio? = null,

    val tipoEquipo: String,
    val marca: String,
    val modelo: String,
    val descripcionFalla: String,
    val fechaIngreso: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    val estado: EstadoReparacion = EstadoReparacion.PENDIENTE,

    val diagnostico: String? = null,
    val solucion: String? = null,
    val costo: Double? = null
)


enum class EstadoReparacion {
    PENDIENTE, REVISION, REPARADO, ENTREGADO
}
