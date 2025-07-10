package com.proyectochad.backend.service.impl

import com.proyectochad.backend.model.Componente
import com.proyectochad.backend.model.Reparacion
import com.proyectochad.backend.repository.ComponenteRepository
import com.proyectochad.backend.service.ComponenteService
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class ComponenteServiceImpl(
    private val componenteRepository: ComponenteRepository
) : ComponenteService {

    override fun agregar(componente: Componente): Componente {
        return componenteRepository.save(componente)
    }

    override fun listarPorReparacion(reparacionId: Long): List<Componente> {
        val componentes = componenteRepository.findByReparacionId(reparacionId)
        if (componentes.isEmpty()) {
            throw EntityNotFoundException("No se encontraron componentes para la reparación con ID $reparacionId")
        }
        return componentes
    }

    override fun listarTodos(): List<Componente> {
        return componenteRepository.findAll()
    }

    override fun actualizar(
        id: Long,
        nombre: String,
        descripcion: String,
        precio: Double,
        cantidad: Int,
        reparacion: Reparacion?
    ): Componente {
        val existente = componenteRepository.findById(id).orElseThrow {
            EntityNotFoundException("Componente con ID $id no encontrado")
        }

        val actualizado = existente.copy(
            nombre = nombre,
            descripcion = descripcion,
            precio = precio,
            cantidad = cantidad,
            reparacion = reparacion
        )
        return componenteRepository.save(actualizado)
    }


    override fun eliminar(id: Long) {
        if (!componenteRepository.existsById(id)) {
            throw EntityNotFoundException("Componente con ID $id no existe")
        }
        componenteRepository.deleteById(id)
    }

    override fun buscarPorId(id: Long): Componente {
        return componenteRepository.findById(id).orElseThrow {
            EntityNotFoundException("Componente con ID $id no encontrado")
        }
    }

}
