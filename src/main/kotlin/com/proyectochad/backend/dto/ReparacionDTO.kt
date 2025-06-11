package com.proyectochad.backend.dto

data class ReparacionDTO(
    val id: Long,
    val tipoEquipo: String,
    val marca: String,
    val modelo: String,
    val descripcionFalla: String,
    val fechaIngreso: String,
    val estado: String,
    val diagnostico: String?,
    val solucion: String?,
    val costo: Double?,
    val cliente: UsuarioDTO,
    val tecnico: UsuarioDTO?,
    val servicio: ServicioDTO?,
    val componentes: List<ComponenteResumenDTO>
)
