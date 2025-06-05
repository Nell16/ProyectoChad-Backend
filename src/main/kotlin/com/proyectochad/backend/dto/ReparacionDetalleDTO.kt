// ReparacionDetalleDTO.kt
package com.proyectochad.backend.dto

import com.proyectochad.backend.model.Servicio
import com.proyectochad.backend.model.Usuario

data class ReparacionDetalleDTO(
    val id: Long,
    val usuario: Usuario,
    val tecnico: Usuario?,
    val tipoEquipo: String,
    val marca: String,
    val modelo: String,
    val descripcionFalla: String,
    val fechaIngreso: String,
    val estado: String,
    val diagnostico: String?,
    val solucion: String?,
    val costo: Double?,
    val servicio: Servicio?,
    val componentes: List<ComponenteResumenDTO>
)
