package com.proyectochad.backend.dto

import com.proyectochad.backend.model.Componente
import com.proyectochad.backend.model.Reparacion
import com.proyectochad.backend.model.Usuario
import java.time.LocalDateTime

data class FacturaDetalleDTO(
    val factura: FacturaDTO,
    val componentes: List<ComponenteDTO>
)

data class FacturaDTO(
    val idFactura: Long,
    val fecha: LocalDateTime,
    val total: Double,
    val cliente: Usuario,
    val reparacion: Reparacion
)

data class ComponenteDTO(
    val idComponente: Long,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val cantidad: Int
)
