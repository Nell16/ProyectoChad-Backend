package com.proyectochad.backend.mapper

import com.proyectochad.backend.dto.*
import com.proyectochad.backend.model.Componente
import com.proyectochad.backend.model.Factura

object FacturaMapper {

    fun toDTO(factura: Factura): FacturaDTO = FacturaDTO(
        idFactura = factura.id,
        fecha = factura.fecha,
        total = factura.total,
        cliente = factura.cliente,
        reparacion = factura.reparacion
    )

    fun toDetalleDTO(factura: Factura, componentes: List<ComponenteResumenDTO>): FacturaDetalleDTO {
        return FacturaDetalleDTO(
            factura = toDTO(factura),
            componentes = componentes
        )
    }
}
