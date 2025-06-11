package com.proyectochad.backend.controller

import com.proyectochad.backend.dto.*
import com.proyectochad.backend.model.Factura
import com.proyectochad.backend.model.Rol
import com.proyectochad.backend.service.ComponenteService
import com.proyectochad.backend.dto.ComponenteResumenDTO
import com.proyectochad.backend.service.FacturaService
import com.proyectochad.backend.service.ReparacionService
import com.proyectochad.backend.service.UsuarioService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.proyectochad.backend.mapper.FacturaMapper
import com.proyectochad.backend.mapper.ComponenteMapper

@RestController
@RequestMapping("/api/facturas")
class FacturaController(
    private val facturaService: FacturaService,
    private val usuarioService: UsuarioService,
    private val reparacionService: ReparacionService,
    private val componenteService: ComponenteService
) {

    // POST http://localhost:8080/api/facturas
    @PostMapping
    fun crear(@RequestBody @Valid request: FacturaRequestDTO): ResponseEntity<FacturaDetalleDTO> {
        val cliente = usuarioService.buscarPorId(request.clienteId)
            ?: return ResponseEntity.badRequest().build()

        if (cliente.rol != Rol.CLIENTE) {
            return ResponseEntity.status(403).build()
        }

        val reparacion = reparacionService.obtenerPorId(request.reparacionId)

        val servicio = reparacion.servicio
            ?: return ResponseEntity.badRequest().build()

        val precioServicio = servicio.precioBase
        val costoDiagnostico = reparacion.costo ?: 0.0
        val componentes = componenteService.listarPorReparacion(reparacion.id)
        val precioComponentes = componentes.sumOf { it.precio * it.cantidad }
        val totalCalculado = precioServicio + costoDiagnostico + precioComponentes

        val factura = Factura(
            total = totalCalculado,
            cliente = cliente,
            reparacion = reparacion
        )

        val creada = facturaService.crear(factura)

        val componentesDTO = componentes.map { ComponenteMapper.toDTO(it) }
        val respuesta = FacturaMapper.toDetalleDTO(creada, componentesDTO)

        return ResponseEntity.ok(respuesta)
    }



    // GET http://localhost:8080/api/facturas/cliente/{clienteId}
    @GetMapping("/cliente/{clienteId}")
    fun listarPorCliente(@PathVariable clienteId: Long): ResponseEntity<List<FacturaDTO>> {
        val facturas = facturaService.listarPorCliente(clienteId)
        val dtos = facturas.map { FacturaMapper.toDTO(it) }
        return ResponseEntity.ok(dtos)
    }

    // GET http://localhost:8080/api/facturas/{id}/componentes
    @GetMapping("/{facturaId}/componentes")
    fun listarComponentesPorFactura(@PathVariable facturaId: Long): ResponseEntity<List<ComponenteResumenDTO>> {
        val factura = facturaService.buscarPorId(facturaId)
            ?: return ResponseEntity.notFound().build()

        val componentes = componenteService.listarPorReparacion(factura.reparacion.id)
        val dtos = componentes.map { ComponenteMapper.toDTO(it) }
        return ResponseEntity.ok(dtos)
    }

}
