package com.proyectochad.backend.service

import com.proyectochad.backend.model.Seguimiento

interface SeguimientoService {
    fun agregar(seguimiento: Seguimiento): Seguimiento
    fun listarPorReparacion(reparacionId: Long): List<Seguimiento>
}
