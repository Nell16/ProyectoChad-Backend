package com.proyectochad.backend.mapper

import com.proyectochad.backend.dto.ComponenteResumenDTO
import com.proyectochad.backend.model.Componente

object ComponenteMapper {
    fun toDTO(componente: Componente): ComponenteResumenDTO = ComponenteResumenDTO(
        id = componente.id,
        nombre = componente.nombre,
        descripcion = componente.descripcion,
        precio = componente.precio,
        cantidad = componente.cantidad
    )
}
