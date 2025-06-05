package com.proyectochad.backend.service.impl

import com.proyectochad.backend.dto.LoginResponseDTO
import com.proyectochad.backend.model.Rol
import com.proyectochad.backend.model.Usuario
import com.proyectochad.backend.repository.UsuarioRepository
import com.proyectochad.backend.security.JwtService
import com.proyectochad.backend.service.UsuarioService
import org.springframework.stereotype.Service

@Service
class UsuarioServiceImpl(
    private val usuarioRepository: UsuarioRepository,
    private val jwtService: JwtService
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

    override fun loginYGenerarToken(correo: String, contrasena: String): LoginResponseDTO? {
        val usuario = usuarioRepository.findByCorreo(correo)
            ?: return null

        if (usuario.contrasena != contrasena) return null

        val token = jwtService.generarToken(usuario.correo, usuario.rol.name)

        return LoginResponseDTO(
            token = token,
            correo = usuario.correo,
            rol = usuario.rol.name
        )
    }


}
