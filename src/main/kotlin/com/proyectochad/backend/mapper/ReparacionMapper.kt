package com.proyectochad.backend.mapper

import com.proyectochad.backend.dto.*
import com.proyectochad.backend.model.Reparacion

object ReparacionMapper {
    fun toDTO(reparacion: Reparacion): ReparacionDTO = ReparacionDTO(
        id = reparacion.id,
        tipoEquipo = reparacion.tipoEquipo,
        marca = reparacion.marca,
        modelo = reparacion.modelo,
        descripcionFalla = reparacion.descripcionFalla,
        fechaIngreso = reparacion.fechaIngreso.toString(),
        estado = reparacion.estado.name,
        diagnostico = reparacion.diagnostico,
        solucion = reparacion.solucion,
        costo = reparacion.costo,
        cliente = UsuarioMapper.toDTO(reparacion.usuario),
        tecnico = reparacion.tecnico?.let { UsuarioMapper.toDTO(it) },
        servicio = reparacion.servicio?.let {
            ServicioDTO(it.id, it.nombre, it.descripcion)
        },
        componentes = reparacion.componentes.map { componente ->
            ComponenteResumenDTO(
                id = componente.id,
                nombre = componente.nombre,
                descripcion = componente.descripcion,
                precio = componente.precio,
                cantidad = componente.cantidad
            )
        }
    )
}
