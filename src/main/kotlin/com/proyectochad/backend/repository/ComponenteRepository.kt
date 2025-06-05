package com.proyectochad.backend.repository

import com.proyectochad.backend.model.Componente
import org.springframework.data.jpa.repository.JpaRepository

interface ComponenteRepository : JpaRepository<Componente, Long> {
    fun findByReparacionId(reparacionId: Long): List<Componente>
}
