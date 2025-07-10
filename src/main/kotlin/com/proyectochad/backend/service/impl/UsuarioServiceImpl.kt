package com.proyectochad.backend.service.impl

import com.proyectochad.backend.dto.LoginResponseDTO
import com.proyectochad.backend.model.Rol
import com.proyectochad.backend.model.Usuario
import com.proyectochad.backend.repository.UsuarioRepository
import com.proyectochad.backend.security.JwtService
import com.proyectochad.backend.service.UsuarioService
import org.springframework.stereotype.Service
import jakarta.persistence.EntityNotFoundException

@Service
class UsuarioServiceImpl(
    private val usuarioRepository: UsuarioRepository,
    private val jwtService: JwtService
) : UsuarioService {

    override fun registrar(usuario: Usuario): Usuario {
        val existente = usuarioRepository.findByCorreo(usuario.correo)
        if (existente != null) {
            throw IllegalArgumentException("Ya existe un usuario con el correo: ${usuario.correo}")
        }
        return usuarioRepository.save(usuario)
    }


    override fun buscarPorId(id: Long): Usuario {
        return usuarioRepository.findById(id).orElseThrow {
            EntityNotFoundException("Usuario con ID $id no encontrado")
        }
    }

    override fun listarPorRol(rol: String): List<Usuario> {
        return usuarioRepository.findAll()
            .filter { it.rol.name.equals(rol.uppercase(), ignoreCase = true) }
    }

    override fun actualizarRol(id: Long, nuevoRol: String): Usuario {
        val usuario = usuarioRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Usuario con ID $id no encontrado") }

        val rolActualizado = try {
            Rol.valueOf(nuevoRol.uppercase())
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Rol inválido: '$nuevoRol'. Usa: CLIENTE, TECNICO o ADMIN.")
        }

        val usuarioActualizado = usuario.copy(rol = rolActualizado)
        return usuarioRepository.save(usuarioActualizado)
    }

    override fun loginYGenerarToken(correo: String, contrasena: String): LoginResponseDTO {
        val usuario = usuarioRepository.findByCorreo(correo)
            ?: throw EntityNotFoundException("Usuario con correo '$correo' no encontrado")
        if (usuario.contrasena != contrasena) {
            throw IllegalArgumentException("Contraseña incorrecta para el usuario '$correo'")
        }
        val token = jwtService.generarToken(usuario.id, usuario.correo, usuario.rol.name)
        return LoginResponseDTO(
            token = token,
            idUsuario = usuario.id,
            correo = usuario.correo,
            rol = usuario.rol.name,
            primerNombre = usuario.primerNombre,
            segundoNombre = usuario.segundoNombre,
            primerApellido = usuario.primerApellido,
            segundoApellido = usuario.segundoApellido,
            telefono = usuario.telefono,
            fotoPerfilUrl = usuario.fotoPerfilUrl
        )
    }

    override fun obtenerTodos(): List<Usuario> {
        return usuarioRepository.findAll()
    }

    override fun buscarPorCorreo(correo: String): Usuario? {
        return usuarioRepository.findByCorreo(correo)
    }

    override fun actualizarDatosPerfil(id: Long, datosActualizados: Usuario): Usuario {
        val existente = usuarioRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Usuario con ID $id no encontrado") }

        val actualizado = existente.copy(
            primerNombre = datosActualizados.primerNombre,
            segundoNombre = datosActualizados.segundoNombre,
            primerApellido = datosActualizados.primerApellido,
            segundoApellido = datosActualizados.segundoApellido,
            correo = datosActualizados.correo,
            telefono = datosActualizados.telefono,
            fotoPerfilUrl = datosActualizados.fotoPerfilUrl
        )

        return usuarioRepository.save(actualizado)
    }
}
