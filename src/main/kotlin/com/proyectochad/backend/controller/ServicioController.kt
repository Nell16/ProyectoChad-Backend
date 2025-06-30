package com.proyectochad.backend.controller

import com.proyectochad.backend.dto.ServicioDTO
import com.proyectochad.backend.dto.ServicioRequestDTO
import com.proyectochad.backend.mapper.ServicioMapper
import com.proyectochad.backend.model.Servicio
import com.proyectochad.backend.service.ServicioService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/servicios")
class ServicioController(
    private val servicioService: ServicioService
) {

    // POST http://localhost:8080/api/servicios
    @PostMapping
    fun crear(@RequestBody @Valid request: ServicioRequestDTO): ResponseEntity<Servicio> {
        val nuevo = Servicio(
            nombre = request.nombre,
            descripcion = request.descripcion,
            precioBase = request.precioBase
        )
        return ResponseEntity.ok(servicioService.crear(nuevo))
    }

    // GET http://localhost:8080/api/servicios
    @GetMapping
    fun listar(): ResponseEntity<List<ServicioDTO>> {
        val servicios = servicioService.listar()
        val dtos = servicios.map { ServicioMapper.toDTO(it) }
        return ResponseEntity.ok(dtos)
    }


    // GET http://localhost:8080/api/servicios/{id}
    @GetMapping("/{id}")
    fun buscar(@PathVariable id: Long): ResponseEntity<ServicioDTO> {
        val servicio = servicioService.buscarPorId(id)
        return ResponseEntity.ok(ServicioMapper.toDTO(servicio))
    }

    //PUT http://localhost:8080/api/servicios/{id}
    @PutMapping("/{id}")
    fun actualizarServicio(
        @PathVariable id: Long,
        @RequestBody @Valid dto: ServicioRequestDTO
    ): ResponseEntity<ServicioDTO> {
        val actualizado = servicioService.actualizar(id, dto)
        return ResponseEntity.ok(ServicioMapper.toDTO(actualizado))
    }

    //DELETE http://localhost:8080/api/servicios/{id}
    @DeleteMapping("/{id}")
    fun eliminarServicio(@PathVariable id: Long): ResponseEntity<Void> {
        servicioService.eliminar(id)
        return ResponseEntity.noContent().build()
    }

}
