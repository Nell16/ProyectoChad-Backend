package com.proyectochad.backend.service

import com.proyectochad.backend.model.Componente

interface ComponenteService {
    fun agregar(componente: Componente): Componente
    fun listarPorReparacion(reparacionId: Long): List<Componente>
}
