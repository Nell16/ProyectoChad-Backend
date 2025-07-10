package com.proyectochad.backend.model

import jakarta.persistence.*
import jakarta.validation.constraints.*
import java.time.LocalDateTime

@Entity
data class Reparacion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    @field:NotNull
    val usuario: Usuario,

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    val tecnico: Usuario? = null,

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    val servicio: Servicio? = null,

    @field:NotBlank
    @field:Size(max = 50)
    val tipoEquipo: String,

    @field:NotBlank
    @field:Size(max = 50)
    val marca: String,

    @field:NotBlank
    @field:Size(max = 50)
    val modelo: String,

    @field:NotBlank
    @field:Size(max = 255)
    val descripcionFalla: String,

    @field:PastOrPresent
    val fechaIngreso: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @field:NotNull
    val estado: EstadoReparacion = EstadoReparacion.PENDIENTE,

    val diagnostico: String? = null,
    val solucion: String? = null,

    @field:PositiveOrZero
    val costo: Double? = null,

    @OneToMany(
        mappedBy = "reparacion",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    val componentes: List<Componente> = emptyList()
)

enum class EstadoReparacion {
    PENDIENTE, REVISION, REPARADO, ENTREGADO
}
