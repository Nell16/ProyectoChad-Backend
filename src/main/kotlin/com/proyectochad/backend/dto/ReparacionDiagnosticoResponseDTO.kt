// ReparacionDiagnosticoResponseDTO.kt
package com.proyectochad.backend.dto

import com.proyectochad.backend.model.Componente
import com.proyectochad.backend.model.Reparacion

data class ReparacionDiagnosticoResponseDTO(
    val reparacion: Reparacion,
    val componentes: List<Componente>
)
