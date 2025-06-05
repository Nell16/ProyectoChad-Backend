package com.proyectochad.backend.controller

import com.proyectochad.backend.model.Servicio
import com.proyectochad.backend.service.ServicioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/servicios")
class ServicioController(
    private val servicioService: ServicioService
) {

    // POST http://localhost:8080/api/servicios
    @PostMapping
    fun crear(@RequestBody servicio: Servicio): ResponseEntity<Servicio> {
        return ResponseEntity.ok(servicioService.crear(servicio))
    }

    // GET http://localhost:8080/api/servicios
    @GetMapping
    fun listar(): ResponseEntity<List<Servicio>> {
        return ResponseEntity.ok(servicioService.listar())
    }

    // GET http://localhost:8080/api/servicios/{id}
    @GetMapping("/{id}")
    fun buscar(@PathVariable id: Long): ResponseEntity<Servicio> {
        val servicio = servicioService.buscarPorId(id)
        return if (servicio != null) ResponseEntity.ok(servicio)
        else ResponseEntity.notFound().build()
    }
}
