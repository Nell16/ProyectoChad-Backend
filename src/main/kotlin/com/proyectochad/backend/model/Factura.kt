package com.proyectochad.backend.model

import jakarta.persistence.*
import jakarta.validation.constraints.*
import java.time.LocalDateTime

@Entity
data class Factura(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:PastOrPresent(message = "La fecha de la factura no puede estar en el futuro")
    val fecha: LocalDateTime = LocalDateTime.now(),

    @field:Positive(message = "El total de la factura debe ser mayor que 0")
    val total: Double,

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    @field:NotNull(message = "La factura debe estar asociada a un cliente")
    val cliente: Usuario,

    @OneToOne(optional = false)
    @JoinColumn(name = "reparacion_id")
    @field:NotNull(message = "La factura debe estar asociada a una reparación")
    val reparacion: Reparacion
)
