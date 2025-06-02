package com.proyectochad.backend.service.impl

import com.proyectochad.backend.model.Rol
import com.proyectochad.backend.model.Usuario
import com.proyectochad.backend.repository.UsuarioRepository
import com.proyectochad.backend.service.UsuarioService
import org.springframework.stereotype.Service

@Service
class UsuarioServiceImpl(
    private val usuarioRepository: UsuarioRepository
) : UsuarioService {

    override fun registrar(usuario: Usuario): Usuario {
        return usuarioRepository.save(usuario)
    }

    override fun buscarPorId(id: Long): Usuario? {
        return usuarioRepository.findById(id).orElse(null)
    }

    override fun listarPorRol(rol: String): List<Usuario> {
        return usuarioRepository.findAll()
            .filter { it.rol.name.equals(rol.uppercase(), ignoreCase = true) }
    }

    override fun actualizarRol(id: Long, nuevoRol: String): Usuario {
        val usuario = usuarioRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado con ID: $id") }

        val rolActualizado = try {
            Rol.valueOf(nuevoRol.uppercase())
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Rol inválido. Usa: CLIENTE, TECNICO o ADMIN.")
        }

        val usuarioActualizado = usuario.copy(rol = rolActualizado)
        return usuarioRepository.save(usuarioActualizado)
    }

}
