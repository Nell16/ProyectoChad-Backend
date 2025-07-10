package com.proyectochad.backend.service

import com.proyectochad.backend.dto.LoginResponseDTO
import com.proyectochad.backend.model.Usuario

interface UsuarioService {
    fun registrar(usuario: Usuario): Usuario
    fun buscarPorId(id: Long): Usuario?
    fun listarPorRol(rol: String): List<Usuario>
    fun actualizarRol(id: Long, nuevoRol: String): Usuario
    fun loginYGenerarToken(correo: String, contrasena: String): LoginResponseDTO?
    fun obtenerTodos(): List<Usuario>
    fun buscarPorCorreo(correo: String): Usuario?
    fun actualizarDatosPerfil(id: Long, datosActualizados: Usuario): Usuario
}
