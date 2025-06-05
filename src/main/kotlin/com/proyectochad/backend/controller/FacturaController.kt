package com.proyectochad.backend.controller

import com.proyectochad.backend.dto.ComponenteDTO
import com.proyectochad.backend.dto.FacturaDTO
import com.proyectochad.backend.dto.FacturaDetalleDTO
import com.proyectochad.backend.dto.FacturaRequestDTO
import com.proyectochad.backend.model.Factura
import com.proyectochad.backend.model.Rol
import com.proyectochad.backend.service.ComponenteService
import com.proyectochad.backend.service.FacturaService
import com.proyectochad.backend.service.ReparacionService
import com.proyectochad.backend.service.UsuarioService
import com.proyectochad.backend.utils.orZero
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

        val facturaDto = FacturaDTO(
            idFactura = creada.id,
            fecha = creada.fecha,
            total = creada.total,
            cliente = creada.cliente,
            reparacion = creada.reparacion
        )

        val componentesDTO = componentes.map {
            ComponenteDTO(
                idComponente = it.id,
                nombre = it.nombre,
                descripcion = it.descripcion,
                precio = it.precio,
                cantidad = it.cantidad
            )
        }

        return ResponseEntity.ok(FacturaDetalleDTO(factura = facturaDto, componentes = componentesDTO))
    }



    // GET http://localhost:8080/api/facturas/cliente/{clienteId}
    @GetMapping("/cliente/{clienteId}")
    fun listarPorCliente(@PathVariable clienteId: Long): ResponseEntity<List<Factura>> {
        return ResponseEntity.ok(facturaService.listarPorCliente(clienteId))
    }

    // GET http://localhost:8080/api/facturas/{id}/componentes
    @GetMapping("/{facturaId}/componentes")
    fun listarComponentesPorFactura(@PathVariable facturaId: Long): ResponseEntity<Any> {
        val factura = facturaService.buscarPorId(facturaId)
            ?: return ResponseEntity.notFound().build()

        val reparacion = factura.reparacion
        val componentes = componenteService.listarPorReparacion(reparacion.id)

        return ResponseEntity.ok(componentes)
    }

}
