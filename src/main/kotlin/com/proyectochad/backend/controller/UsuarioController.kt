package com.proyectochad.backend.controller

import com.proyectochad.backend.dto.LoginRequestDTO
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
    fun registrarUsuario(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        return ResponseEntity.ok(usuarioService.registrar(usuario))
    }

    //POST http://localhost:8080/api/usuarios/login
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequestDTO): ResponseEntity<Usuario> {
        val usuario = usuarioService.login(loginRequest.correo, loginRequest.contrasena)
        return if (usuario != null) ResponseEntity.ok(usuario)
        else ResponseEntity.status(401).build()
    }

    //GET http://localhost:8080/api/usuarios/{id}
    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): ResponseEntity<Usuario> {
        val usuario = usuarioService.buscarPorId(id)
        return if (usuario != null) ResponseEntity.ok(usuario)
        else ResponseEntity.notFound().build()
    }

    //GET http://localhost:8080/api/usuarios/por-rol?rol=CLIENTE
    @GetMapping("/por-rol")
    fun listarPorRol(@RequestParam rol: String): ResponseEntity<List<Usuario>> {
        return ResponseEntity.ok(usuarioService.listarPorRol(rol))
    }

    //http://localhost:8080/api/usuarios/{id}/rol?nuevoRol=TECNICO
    @PutMapping("/{id}/rol")
    fun actualizarRol(
        @PathVariable id: Long,
        @RequestParam nuevoRol: String
    ): ResponseEntity<Usuario> {
        return ResponseEntity.ok(usuarioService.actualizarRol(id, nuevoRol))
    }

}
