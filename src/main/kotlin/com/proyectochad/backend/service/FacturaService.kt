package com.proyectochad.backend.service

import com.proyectochad.backend.model.Factura

interface FacturaService {
    fun crear(factura: Factura): Factura
    fun listarPorCliente(clienteId: Long): List<Factura>
    fun buscarPorId(id: Long): Factura?
}
