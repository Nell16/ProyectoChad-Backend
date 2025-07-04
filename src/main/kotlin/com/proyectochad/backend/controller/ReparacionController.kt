package com.proyectochad.backend.controller

import com.proyectochad.backend.dto.*
import com.proyectochad.backend.mapper.ReparacionMapper
import com.proyectochad.backend.model.EstadoReparacion
import com.proyectochad.backend.model.Reparacion
import com.proyectochad.backend.model.Rol
import com.proyectochad.backend.service.ComponenteService
import com.proyectochad.backend.service.ReparacionService
import com.proyectochad.backend.service.ServicioService
import com.proyectochad.backend.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder

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
    fun obtenerTodas(): ResponseEntity<List<ReparacionDTO>> {
        val reparaciones = reparacionService.obtenerTodas()
        val dtos = reparaciones.map { ReparacionMapper.toDTO(it) }
        return ResponseEntity.ok(dtos)
    }


    //GET http://localhost:8080/api/reparaciones/{id}
    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long): ResponseEntity<ReparacionDTO> {
        val reparacion = reparacionService.obtenerPorId(id)
        return ResponseEntity.ok(ReparacionMapper.toDTO(reparacion))
    }


    //GET http://localhost:8080/api/reparaciones/usuario/{usuarioId}
    @GetMapping("/usuario/{usuarioId}")
    fun obtenerPorUsuario(@PathVariable usuarioId: Long): ResponseEntity<List<ReparacionDTO>> {
        val reparaciones = reparacionService.listarPorUsuarioId(usuarioId)
        val dtos = reparaciones.map { ReparacionMapper.toDTO(it) }
        return ResponseEntity.ok(dtos)
    }

    //GET http://localhost:8080/api/reparaciones/tecnico/{idUsuario}
    @GetMapping("/tecnico/{tecnicoId}")
    fun obtenerPorTecnico(@PathVariable tecnicoId: Long): ResponseEntity<List<ReparacionDTO>> {
        val reparaciones = reparacionService.listarPorTecnicoId(tecnicoId)
        val dtos = reparaciones.map { ReparacionMapper.toDTO(it) }
        return ResponseEntity.ok(dtos)
    }

    //PUT http://localhost:8080/api/reparaciones/{idReparacion}/asignar-tecnico?tecnicoId={idUsuario}
    @PutMapping("/{reparacionId}/asignar-tecnico")
    fun asignarTecnico(
        @PathVariable reparacionId: Long,
        @RequestParam tecnicoId: Long
    ): ResponseEntity<Reparacion> {
        val auth = SecurityContextHolder.getContext().authentication
        val rol = auth.authorities.first().authority

        if (rol != "ROLE_ADMIN") {
            return ResponseEntity.status(403).build()
        }

        return ResponseEntity.ok(reparacionService.asignarTecnico(reparacionId, tecnicoId))
    }


    //PUT http://localhost:8080/api/reparaciones/{id}/estado?nuevoEstado=REPARADO
    @PutMapping("/{id}/estado")
    fun actualizarEstado(
        @PathVariable id: Long,
        @RequestParam nuevoEstado: String
    ): ResponseEntity<Reparacion> {
        val auth = SecurityContextHolder.getContext().authentication
        val rol = auth.authorities.first().authority

        if (rol != "ROLE_TECNICO" && rol != "ROLE_ADMIN") {
            return ResponseEntity.status(403).build()
        }

        return ResponseEntity.ok(reparacionService.actualizarEstado(id, nuevoEstado))
    }


    //PUT http://localhost:8080/api/reparaciones/{idReparacion}/diagnostico
    @PutMapping("/{id}/diagnostico")
    fun actualizarDiagnostico(
        @PathVariable id: Long,
        @RequestBody dto: ReparacionDiagnosticoDTO
    ): ResponseEntity<ReparacionDetalleDTO> {
        val auth = SecurityContextHolder.getContext().authentication
        val rol = auth.authorities.first().authority

        if (rol != "ROLE_TECNICO" && rol != "ROLE_ADMIN") {
            return ResponseEntity.status(403).build()
        }
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
            componentes = componentes
        )
        return ResponseEntity.ok(respuesta)
    }

    // PUT http://localhost:8080/api/reparaciones/{id}/asignar-servicio
    @PutMapping("/{id}/asignar-servicio")
    fun asignarServicio(
        @PathVariable id: Long,
        @RequestBody @Valid dto: AsignarServicioDTO
    ): ResponseEntity<Reparacion> {
        val auth = SecurityContextHolder.getContext().authentication
        val rol = auth.authorities.first().authority

        if (rol != "ROLE_TECNICO" && rol != "ROLE_ADMIN") {
            return ResponseEntity.status(403).build()
        }

        val servicio = servicioService.buscarPorId(dto.servicioId)
            ?: return ResponseEntity.badRequest().build()

        val actualizada = reparacionService.asignarServicio(id, servicio)
        return ResponseEntity.ok(actualizada)
    }

    // GET http://localhost:8080/api/reparaciones/sin-tecnico
    @GetMapping("/sin-tecnico")
    fun listarSinTecnico(): ResponseEntity<List<ReparacionDTO>> {
        val sinTecnico = reparacionService.obtenerSinTecnico()
        val dtos = sinTecnico.map { ReparacionMapper.toDTO(it) }
        return ResponseEntity.ok(dtos)
    }


    @PutMapping("/{reparacionId}/autoasignar")
    fun autoasignarTecnico(
        @PathVariable reparacionId: Long
    ): ResponseEntity<Reparacion> {
        val auth = SecurityContextHolder.getContext().authentication
        val correo = auth.name
        val usuario = usuarioService.buscarPorCorreo(correo) ?: return ResponseEntity.status(401).build()

        if (usuario.rol != Rol.TECNICO) {
            return ResponseEntity.status(403).build()
        }

        // Revisar si ya tiene 5 reparaciones activas
        val activas = reparacionService.listarPorTecnicoId(usuario.id)
            .filter { it.estado != EstadoReparacion.REPARADO && it.estado != EstadoReparacion.ENTREGADO }

        if (activas.size >= 5) {
            return ResponseEntity.badRequest().body(null)
        }

        // Asignar si todo está en orden
        val reparacion = reparacionService.asignarTecnico(reparacionId, usuario.id)
        return ResponseEntity.ok(reparacion)
    }




}
