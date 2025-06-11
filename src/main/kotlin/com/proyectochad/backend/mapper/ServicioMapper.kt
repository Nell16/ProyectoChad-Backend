package com.proyectochad.backend.mapper

import com.proyectochad.backend.dto.ServicioDTO
import com.proyectochad.backend.model.Servicio

object ServicioMapper {
    fun toDTO(servicio: Servicio): ServicioDTO = ServicioDTO(
        id = servicio.id,
        nombre = servicio.nombre,
        descripcion = servicio.descripcion
    )
}
