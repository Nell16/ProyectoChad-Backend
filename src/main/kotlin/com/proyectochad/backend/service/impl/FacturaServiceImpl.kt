package com.proyectochad.backend.service.impl

import com.proyectochad.backend.model.Factura
import com.proyectochad.backend.repository.FacturaRepository
import com.proyectochad.backend.service.FacturaService
import org.springframework.stereotype.Service

@Service
class FacturaServiceImpl(
    private val facturaRepository: FacturaRepository
) : FacturaService {

    override fun crear(factura: Factura): Factura {
        return facturaRepository.save(factura)
    }

    override fun listarPorCliente(clienteId: Long): List<Factura> {
        return facturaRepository.findByClienteId(clienteId)
    }

    override fun buscarPorId(id: Long): Factura? {
        return facturaRepository.findById(id).orElse(null)
    }

}
