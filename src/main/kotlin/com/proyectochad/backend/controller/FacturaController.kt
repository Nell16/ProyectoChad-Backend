package com.proyectochad.backend.controller

import com.proyectochad.backend.dto.FacturaRequestDTO
import com.proyectochad.backend.model.Factura
import com.proyectochad.backend.model.Rol
import com.proyectochad.backend.service.FacturaService
import com.proyectochad.backend.service.ReparacionService
import com.proyectochad.backend.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/facturas")
class FacturaController(
    private val facturaService: FacturaService,
    private val usuarioService: UsuarioService,
    private val reparacionService: ReparacionService
) {

    @PostMapping
    fun crear(@RequestBody request: FacturaRequestDTO): ResponseEntity<Factura> {
        val cliente = usuarioService.buscarPorId(request.clienteId)
            ?: return ResponseEntity.badRequest().build()

        if (cliente.rol != Rol.CLIENTE) {
            return ResponseEntity.status(403).build()
        }

        val reparacion = reparacionService.obtenerPorId(request.reparacionId)

        val factura = Factura(
            total = request.total,
            cliente = cliente,
            reparacion = reparacion
        )

        return ResponseEntity.ok(facturaService.crear(factura))
    }

    @GetMapping("/cliente/{clienteId}")
    fun listarPorCliente(@PathVariable clienteId: Long): ResponseEntity<List<Factura>> {
        return ResponseEntity.ok(facturaService.listarPorCliente(clienteId))
    }
}
