package com.proyectochad.backend.service.impl

import com.proyectochad.backend.model.Componente
import com.proyectochad.backend.repository.ComponenteRepository
import com.proyectochad.backend.service.ComponenteService
import org.springframework.stereotype.Service
import jakarta.persistence.EntityNotFoundException

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
}
