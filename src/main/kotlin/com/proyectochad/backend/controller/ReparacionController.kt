package com.proyectochad.backend.controller

import com.proyectochad.backend.dto.ReparacionRequestDTO
import com.proyectochad.backend.model.Reparacion
import com.proyectochad.backend.service.ReparacionService
import com.proyectochad.backend.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reparaciones")
class ReparacionController(
    private val reparacionService: ReparacionService,
    private val usuarioService: UsuarioService
) {

    //http://localhost:8080/api/reparaciones
    @PostMapping
    fun crearReparacion(@RequestBody request: ReparacionRequestDTO): ResponseEntity<Reparacion> {
        val usuario = usuarioService.buscarPorId(request.usuarioId)
            ?: return ResponseEntity.badRequest().build()
        val nueva = Reparacion(
            usuario = usuario,
            tipoEquipo = request.tipoEquipo,
            marca = request.marca,
            modelo = request.modelo,
            descripcionFalla = request.descripcionFalla
        )
        return ResponseEntity.ok(reparacionService.crearReparacion(nueva))
    }

    //http://localhost:8080/api/reparaciones
    @GetMapping
    fun obtenerTodas(): ResponseEntity<List<Reparacion>> {
        return ResponseEntity.ok(reparacionService.obtenerTodas())
    }

    //http://localhost:8080/api/reparaciones/{id}
    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long): ResponseEntity<Reparacion> {
        return ResponseEntity.ok(reparacionService.obtenerPorId(id))
    }

    //http://localhost:8080/api/reparaciones/{id}/estado?nuevoEstado=REPARADO
    @PutMapping("/{id}/estado")
    fun actualizarEstado(
        @PathVariable id: Long,
        @RequestParam nuevoEstado: String
    ): ResponseEntity<Reparacion> {
        return ResponseEntity.ok(reparacionService.actualizarEstado(id, nuevoEstado))
    }

    //http://localhost:8080/api/reparaciones/usuario/{usuarioId}
    @GetMapping("/usuario/{usuarioId}")
    fun obtenerPorUsuario(@PathVariable usuarioId: Long): ResponseEntity<List<Reparacion>> {
        return ResponseEntity.ok(reparacionService.listarPorUsuarioId(usuarioId))
    }

    @PutMapping("/{reparacionId}/asignar-tecnico")
    fun asignarTecnico(
        @PathVariable reparacionId: Long,
        @RequestParam tecnicoId: Long
    ): ResponseEntity<Reparacion> {
        return ResponseEntity.ok(reparacionService.asignarTecnico(reparacionId, tecnicoId))
    }

    @GetMapping("/tecnico/{tecnicoId}")
    fun obtenerPorTecnico(@PathVariable tecnicoId: Long): ResponseEntity<List<Reparacion>> {
        return ResponseEntity.ok(reparacionService.listarPorTecnicoId(tecnicoId))
    }

}
