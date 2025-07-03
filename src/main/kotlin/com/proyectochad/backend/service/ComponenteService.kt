package com.proyectochad.backend.service

import com.proyectochad.backend.model.Componente
import com.proyectochad.backend.model.Reparacion

interface ComponenteService {
    fun agregar(componente: Componente): Componente
    fun listarPorReparacion(reparacionId: Long): List<Componente>
    fun listarTodos(): List<Componente>
    fun actualizar(
        id: Long,
        nombre: String,
        descripcion: String,
        precio: Double,
        cantidad: Int,
        reparacion: Reparacion?
    ): Componente
    fun eliminar(id: Long)
}
