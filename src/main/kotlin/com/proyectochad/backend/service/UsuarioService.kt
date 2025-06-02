package com.proyectochad.backend.service

import com.proyectochad.backend.model.Usuario

interface UsuarioService {
    fun registrar(usuario: Usuario): Usuario
    fun buscarPorId(id: Long): Usuario?
    fun listarPorRol(rol: String): List<Usuario>
    fun actualizarRol(id: Long, nuevoRol: String): Usuario
}
