package com.proyectochad.backend.service.impl

import com.proyectochad.backend.model.Componente
import com.proyectochad.backend.repository.ComponenteRepository
import com.proyectochad.backend.service.ComponenteService
import org.springframework.stereotype.Service

@Service
class ComponenteServiceImpl(
    private val componenteRepository: ComponenteRepository
) : ComponenteService {

    override fun agregar(componente: Componente): Componente {
        return componenteRepository.save(componente)
    }

    override fun listarPorReparacion(reparacionId: Long): List<Componente> {
        return componenteRepository.findByReparacionId(reparacionId)
    }
}
