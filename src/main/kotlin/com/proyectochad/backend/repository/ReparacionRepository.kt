package com.proyectochad.backend.repository

import com.proyectochad.backend.model.Reparacion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReparacionRepository : JpaRepository<Reparacion, Long>{
    fun findByTecnicoIsNull(): List<Reparacion>
}



