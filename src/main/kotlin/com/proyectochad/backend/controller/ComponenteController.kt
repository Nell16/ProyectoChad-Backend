package com.proyectochad.backend.controller

import com.proyectochad.backend.dto.ComponenteGeneralDTO
import com.proyectochad.backend.dto.ComponenteRequestDTO
import com.proyectochad.backend.dto.ComponenteResumenDTO
import com.proyectochad.backend.mapper.ComponenteMapper
import com.proyectochad.backend.model.Componente
import com.proyectochad.backend.service.ComponenteService
import com.proyectochad.backend.service.ReparacionService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/componentes")
class ComponenteController(
    private val componenteService: ComponenteService,
    private val reparacionService: ReparacionService
) {

    @PostMapping
    fun agregar(@RequestBody @Valid request: ComponenteRequestDTO): ResponseEntity<Componente> {
        val reparacion = reparacionService.obtenerPorId(request.reparacionId)

        val componente = Componente(
            nombre = request.nombre,
            descripcion = request.descripcion,
            precio = request.precio,
            cantidad = request.cantidad,
            reparacion = reparacion
        )

        return ResponseEntity.ok(componenteService.agregar(componente))
    }

    @GetMapping("/reparacion/{reparacionId}")
    fun listarPorReparacion(@PathVariable reparacionId: Long): ResponseEntity<List<ComponenteResumenDTO>> {
        val componentes = componenteService.listarPorReparacion(reparacionId)
        val dtos = componentes.map { ComponenteMapper.toDTO(it) }
        return ResponseEntity.ok(dtos)
    }

    //listar todos los componentes para admin en el frontend
    @GetMapping
    fun listarTodos(): ResponseEntity<List<ComponenteResumenDTO>> {
        val componentes = componenteService.listarTodos()
        val dtos = componentes.map { ComponenteMapper.toDTO(it) }
        return ResponseEntity.ok(dtos)
    }

    @PutMapping("/{id}/general")
    fun actualizarGeneral(
        @PathVariable id: Long,
        @RequestBody @Valid dto: ComponenteGeneralDTO
    ): ResponseEntity<ComponenteResumenDTO> {
        val actualizado = componenteService.actualizar(
            id,
            nombre = dto.nombre,
            descripcion = dto.descripcion,
            precio = dto.precio,
            cantidad = dto.cantidad,
            reparacion = null
        )
        return ResponseEntity.ok(ComponenteMapper.toDTO(actualizado))
    }


    @DeleteMapping("/{id}")
    fun eliminarComponente(@PathVariable id: Long): ResponseEntity<Void> {
        componenteService.eliminar(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/generales")
    fun crearGeneral(@RequestBody @Valid dto: ComponenteGeneralDTO): ResponseEntity<ComponenteResumenDTO> {
        val componente = Componente(
            nombre = dto.nombre,
            descripcion = dto.descripcion,
            precio = dto.precio,
            cantidad = dto.cantidad,
            reparacion = null
        )
        val creado = componenteService.agregar(componente)
        return ResponseEntity.ok(ComponenteMapper.toDTO(creado))
    }



}
