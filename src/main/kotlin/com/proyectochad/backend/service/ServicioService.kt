package com.proyectochad.backend.service

import com.proyectochad.backend.model.Servicio

interface ServicioService {
    fun crear(servicio: Servicio): Servicio
    fun listar(): List<Servicio>
    fun buscarPorId(id: Long): Servicio
}
