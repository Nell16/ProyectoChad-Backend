package com.proyectochad.backend.service.impl

import com.proyectochad.backend.model.Seguimiento
import com.proyectochad.backend.repository.SeguimientoRepository
import com.proyectochad.backend.service.SeguimientoService
import org.springframework.stereotype.Service

@Service
class SeguimientoServiceImpl(
    private val seguimientoRepository: SeguimientoRepository
) : SeguimientoService {

    override fun agregar(seguimiento: Seguimiento): Seguimiento {
        return seguimientoRepository.save(seguimiento)
    }

    override fun listarPorReparacion(reparacionId: Long): List<Seguimiento> {
        return seguimientoRepository.findByReparacionId(reparacionId)
    }
}
