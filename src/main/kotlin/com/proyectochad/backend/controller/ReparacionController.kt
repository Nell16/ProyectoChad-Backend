package com.proyectochad.backend.controller

import com.proyectochad.backend.dto.*
import com.proyectochad.backend.model.Reparacion
import com.proyectochad.backend.service.ComponenteService
import com.proyectochad.backend.service.ReparacionService
import com.proyectochad.backend.service.ServicioService
import com.proyectochad.backend.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/reparaciones")
class ReparacionController(
    private val reparacionService: ReparacionService,
    private val usuarioService: UsuarioService,
    private val servicioService: ServicioService,
    private val componenteService: ComponenteService
) {

    // POST http://localhost:8080/api/reparaciones
    @PostMapping
    fun crearReparacion(@RequestBody @Valid request: ReparacionRequestDTO): ResponseEntity<Reparacion> {
        val usuario = usuarioService.buscarPorId(request.usuarioId)
            ?: return ResponseEntity.badRequest().build()

        val nueva = Reparacion(
            usuario = usuario,
            tipoEquipo = request.tipoEquipo,
            marca = request.marca,
            modelo = request.modelo,
            descripcionFalla = request.descripcionFalla,
            servicio = null  // no se asigna aún
        )

        return ResponseEntity.ok(reparacionService.crearReparacion(nueva))
    }

    //GET http://localhost:8080/api/reparaciones
    @GetMapping
    fun obtenerTodas(): ResponseEntity<List<Reparacion>> {
        return ResponseEntity.ok(reparacionService.obtenerTodas())
    }

    //GET http://localhost:8080/api/reparaciones/{id}
    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long): ResponseEntity<Reparacion> {
        return ResponseEntity.ok(reparacionService.obtenerPorId(id))
    }

    //GET http://localhost:8080/api/reparaciones/usuario/{usuarioId}
    @GetMapping("/usuario/{usuarioId}")
    fun obtenerPorUsuario(@PathVariable usuarioId: Long): ResponseEntity<List<Reparacion>> {
        return ResponseEntity.ok(reparacionService.listarPorUsuarioId(usuarioId))
    }

    //GET http://localhost:8080/api/reparaciones/tecnico/{idUsuario}
    @GetMapping("/tecnico/{tecnicoId}")
    fun obtenerPorTecnico(@PathVariable tecnicoId: Long): ResponseEntity<List<Reparacion>> {
        return ResponseEntity.ok(reparacionService.listarPorTecnicoId(tecnicoId))
    }

    //PUT http://localhost:8080/api/reparaciones/{idReparacion}/asignar-tecnico?tecnicoId={idUsuario}
    @PutMapping("/{reparacionId}/asignar-tecnico")
    fun asignarTecnico(
        @PathVariable reparacionId: Long,
        @RequestParam tecnicoId: Long
    ): ResponseEntity<Reparacion> {
        return ResponseEntity.ok(reparacionService.asignarTecnico(reparacionId, tecnicoId))
    }

    //PUT http://localhost:8080/api/reparaciones/{id}/estado?nuevoEstado=REPARADO
    @PutMapping("/{id}/estado")
    fun actualizarEstado(
        @PathVariable id: Long,
        @RequestParam nuevoEstado: String
    ): ResponseEntity<Reparacion> {
        return ResponseEntity.ok(reparacionService.actualizarEstado(id, nuevoEstado))
    }

    //PUT http://localhost:8080/api/reparaciones/{idReparacion}/diagnostico
    @PutMapping("/{id}/diagnostico")
    fun actualizarDiagnostico(
        @PathVariable id: Long,
        @RequestBody dto: ReparacionDiagnosticoDTO
    ): ResponseEntity<ReparacionDetalleDTO> {
        val reparacion = reparacionService.actualizarDiagnostico(
            id,
            dto.diagnostico,
            dto.solucion,
            dto.costo
        )
        val componentes = componenteService.listarPorReparacion(id)

        val componentesDTO = componentes.map {
            ComponenteResumenDTO(
                id = it.id,
                nombre = it.nombre,
                descripcion = it.descripcion,
                precio = it.precio,
                cantidad = it.cantidad
            )
        }

        val respuesta = ReparacionDetalleDTO(
            id = reparacion.id,
            usuario = reparacion.usuario,
            tecnico = reparacion.tecnico,
            tipoEquipo = reparacion.tipoEquipo,
            marca = reparacion.marca,
            modelo = reparacion.modelo,
            descripcionFalla = reparacion.descripcionFalla,
            fechaIngreso = reparacion.fechaIngreso.toString(),
            estado = reparacion.estado.name,
            diagnostico = reparacion.diagnostico,
            solucion = reparacion.solucion,
            costo = reparacion.costo,
            servicio = reparacion.servicio,
            componentes = componentesDTO
        )
        return ResponseEntity.ok(respuesta)
    }

    // PUT http://localhost:8080/api/reparaciones/{id}/asignar-servicio
    @PutMapping("/{id}/asignar-servicio")
    fun asignarServicio(
        @PathVariable id: Long,
        @RequestBody @Valid dto: AsignarServicioDTO
    ): ResponseEntity<Reparacion> {
        val servicio = servicioService.buscarPorId(dto.servicioId)
            ?: return ResponseEntity.badRequest().build()

        val actualizada = reparacionService.asignarServicio(id, servicio)
        return ResponseEntity.ok(actualizada)
    }


}
