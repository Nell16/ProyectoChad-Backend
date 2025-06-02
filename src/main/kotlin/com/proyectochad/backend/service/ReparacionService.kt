package com.proyectochad.backend.service

import com.proyectochad.backend.model.Reparacion

interface ReparacionService {
    fun crearReparacion(reparacion: Reparacion): Reparacion
    fun obtenerTodas(): List<Reparacion>
    fun obtenerPorId(id: Long): Reparacion
    fun actualizarEstado(id: Long, nuevoEstado: String): Reparacion
    fun listarPorUsuarioId(id: Long): List<Reparacion>
    fun asignarTecnico(reparacionId: Long, tecnicoId: Long): Reparacion
    fun listarPorTecnicoId(tecnicoId: Long): List<Reparacion>
    fun actualizarDiagnostico(id: Long, diagnostico: String, solucion: String, costo: Double): Reparacion

}
