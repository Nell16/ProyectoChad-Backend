package com.proyectochad.backend.controller

import com.proyectochad.backend.dto.SeguimientoRequestDTO
import com.proyectochad.backend.model.EstadoReparacion
import com.proyectochad.backend.model.Rol
import com.proyectochad.backend.model.Seguimiento
import com.proyectochad.backend.service.ReparacionService
import com.proyectochad.backend.service.SeguimientoService
import com.proyectochad.backend.service.UsuarioService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/seguimientos")
class SeguimientoController(
    private val seguimientoService: SeguimientoService,
    private val usuarioService: UsuarioService,
    private val reparacionService: ReparacionService
) {

    // POST http://localhost:8080/api/seguimientos
    @PostMapping
    fun agregar(@RequestBody @Valid request: SeguimientoRequestDTO): ResponseEntity<Seguimiento> {
        val autor = usuarioService.buscarPorId(request.autorId)
            ?: return ResponseEntity.badRequest().build()

        if (autor.rol != Rol.TECNICO && autor.rol != Rol.ADMIN) {
            return ResponseEntity.status(403).build() // 403 Forbidden
        }

        val reparacion = reparacionService.obtenerPorId(request.reparacionId)

        if (reparacion.estado == EstadoReparacion.PENDIENTE) {
            reparacionService.actualizarEstado(reparacion.id, EstadoReparacion.REVISION.name)
        }

        val seguimiento = Seguimiento(
            descripcion = request.descripcion,
            autor = autor,
            reparacion = reparacion
        )

        return ResponseEntity.ok(seguimientoService.agregar(seguimiento))
    }

    // GET http://localhost:8080/api/seguimientos/reparacion/{id}
    @GetMapping("/reparacion/{id}")
    fun listarPorReparacion(@PathVariable id: Long): ResponseEntity<List<Seguimiento>> {
        return ResponseEntity.ok(seguimientoService.listarPorReparacion(id))
    }
}
