package com.proyectochad.backend.service.impl

import com.proyectochad.backend.dto.ServicioRequestDTO
import com.proyectochad.backend.model.Servicio
import com.proyectochad.backend.repository.ServicioRepository
import com.proyectochad.backend.service.ServicioService
import org.springframework.stereotype.Service
import jakarta.persistence.EntityNotFoundException

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

    override fun buscarPorId(id: Long): Servicio {
        return servicioRepository.findById(id).orElseThrow {
            EntityNotFoundException("Servicio con ID $id no encontrado")
        }
    }

    override fun actualizar(id: Long, dto: ServicioRequestDTO): Servicio {
        val existente = buscarPorId(id)
        val actualizado = Servicio(
            id = existente.id,
            nombre = dto.nombre,
            descripcion = dto.descripcion,
            precioBase = dto.precioBase
        )
        return servicioRepository.save(actualizado)
    }


    override fun eliminar(id: Long) {
        if (!servicioRepository.existsById(id)) {
            throw EntityNotFoundException("Servicio con ID $id no existe")
        }
        servicioRepository.deleteById(id)
    }


}
