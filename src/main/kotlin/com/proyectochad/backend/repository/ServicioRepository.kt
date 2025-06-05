package com.proyectochad.backend.repository

import com.proyectochad.backend.model.Servicio
import org.springframework.data.jpa.repository.JpaRepository

interface ServicioRepository : JpaRepository<Servicio, Long>
