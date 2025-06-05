package com.proyectochad.backend.repository

import com.proyectochad.backend.model.Factura
import org.springframework.data.jpa.repository.JpaRepository

interface FacturaRepository : JpaRepository<Factura, Long> {
    fun findByClienteId(clienteId: Long): List<Factura>
}
