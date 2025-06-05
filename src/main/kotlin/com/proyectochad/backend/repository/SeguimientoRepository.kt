package com.proyectochad.backend.repository

import com.proyectochad.backend.model.Seguimiento
import org.springframework.data.jpa.repository.JpaRepository

interface SeguimientoRepository : JpaRepository<Seguimiento, Long> {
    fun findByReparacionId(reparacionId: Long): List<Seguimiento>
}
