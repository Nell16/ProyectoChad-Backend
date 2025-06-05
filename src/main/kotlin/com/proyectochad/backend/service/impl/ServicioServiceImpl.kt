package com.proyectochad.backend.service.impl

import com.proyectochad.backend.model.Servicio
import com.proyectochad.backend.repository.ServicioRepository
import com.proyectochad.backend.service.ServicioService
import org.springframework.stereotype.Service

@Service
class ServicioServiceImpl(
    private val servicioRepository: ServicioRepository
) : ServicioService {

    override fun crear(servicio: Servicio): Servicio {
        return servicioRepository.save(servicio)
    }

    override fun listar(): List<Servicio> {
        return servicioRepository.findAll()
    }

    override fun buscarPorId(id: Long): Servicio? {
        return servicioRepository.findById(id).orElse(null)
    }
}
