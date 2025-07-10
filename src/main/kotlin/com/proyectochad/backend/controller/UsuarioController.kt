package com.proyectochad.backend.controller

import com.proyectochad.backend.dto.LoginRequestDTO
import com.proyectochad.backend.dto.LoginResponseDTO
import com.proyectochad.backend.dto.UsuarioDTO
import com.proyectochad.backend.dto.UsuarioRequestDTO
import com.proyectochad.backend.mapper.UsuarioMapper
import com.proyectochad.backend.model.Rol
import com.proyectochad.backend.model.Usuario
import com.proyectochad.backend.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/usuarios")
class UsuarioController(
    private val usuarioService: UsuarioService
) {

    //POST http://localhost:8080/api/usuarios/registro
    @PostMapping("/registro")
    fun registrarUsuario(@RequestBody request: UsuarioRequestDTO): ResponseEntity<UsuarioDTO> {
        val nuevoUsuario = Usuario(
            id = 0,
            primerNombre = request.primerNombre,
            segundoNombre = request.segundoNombre,
            primerApellido = request.primerApellido,
            segundoApellido = request.segundoApellido,
            correo = request.correo,
            contrasena = request.contrasena,
            telefono = request.telefono,
            fotoPerfilUrl = request.fotoPerfilUrl,
            rol = Rol.valueOf(request.rol)
        )
        val registrado = usuarioService.registrar(nuevoUsuario)
        return ResponseEntity.ok(UsuarioMapper.toDTO(registrado))
    }



    //POST http://localhost:8080/api/usuarios/login
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequestDTO): ResponseEntity<LoginResponseDTO> {
        val respuesta = usuarioService.loginYGenerarToken(loginRequest.correo, loginRequest.contrasena)
        return if (respuesta != null) ResponseEntity.ok(respuesta)
        else ResponseEntity.status(401).build()
    }

    //GET http://localhost:8080/api/usuarios/{id}
    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): ResponseEntity<UsuarioDTO> {
        val usuario = usuarioService.buscarPorId(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(UsuarioMapper.toDTO(usuario))
    }

    //GET http://localhost:8080/api/usuarios/por-rol?rol=CLIENTE
    @GetMapping("/por-rol")
    fun listarPorRol(@RequestParam rol: String): ResponseEntity<List<UsuarioDTO>> {
        val usuarios = usuarioService.listarPorRol(rol)
        val dtos = usuarios.map { UsuarioMapper.toDTO(it) }
        return ResponseEntity.ok(dtos)
    }


    //http://localhost:8080/api/usuarios/{id}/rol?nuevoRol=TECNICO
    @PutMapping("/{id}/rol")
    fun actualizarRol(
        @PathVariable id: Long,
        @RequestParam nuevoRol: String
    ): ResponseEntity<UsuarioDTO> {
        val usuarioActualizado = usuarioService.actualizarRol(id, nuevoRol)
        return ResponseEntity.ok(UsuarioMapper.toDTO(usuarioActualizado))
    }

    @GetMapping
    fun listarUsuarios(): ResponseEntity<List<UsuarioDTO>> {
        val usuarios = usuarioService.obtenerTodos()
        val dtos = usuarios.map { usuario -> UsuarioMapper.toDTO(usuario) }
        return ResponseEntity.ok(dtos)
    }

    @PutMapping("/{id}")
    fun actualizarPerfil(
        @PathVariable id: Long,
        @RequestBody dto: UsuarioDTO
    ): ResponseEntity<UsuarioDTO> {
        val actualizado = Usuario(
            id = id,
            primerNombre = dto.primerNombre,
            segundoNombre = dto.segundoNombre,
            primerApellido = dto.primerApellido,
            segundoApellido = dto.segundoApellido,
            correo = dto.correo,
            contrasena = usuarioService.buscarPorId(id)?.contrasena ?: "", // conservar contraseña
            telefono = dto.telefono,
            fotoPerfilUrl = dto.fotoPerfilUrl,
            rol = Rol.valueOf(dto.rol) // No se actualizará en el service, pero se necesita para la entidad
        )

        val actualizadoFinal = usuarioService.actualizarDatosPerfil(id, actualizado)
        return ResponseEntity.ok(UsuarioMapper.toDTO(actualizadoFinal))
    }




}
