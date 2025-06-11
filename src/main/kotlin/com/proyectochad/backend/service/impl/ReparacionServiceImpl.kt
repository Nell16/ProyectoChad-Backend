package com.proyectochad.backend.service.impl

import com.proyectochad.backend.model.EstadoReparacion
import com.proyectochad.backend.model.Reparacion
import com.proyectochad.backend.repository.ReparacionRepository
import com.proyectochad.backend.service.ReparacionService
import org.springframework.stereotype.Service
import com.proyectochad.backend.repository.UsuarioRepository
import com.proyectochad.backend.model.Rol
import com.proyectochad.backend.model.Servicio
import jakarta.persistence.EntityNotFoundException


@Service
class ReparacionServiceImpl(
    private val reparacionRepository: ReparacionRepository,
    private val usuarioRepository: UsuarioRepository // 👈 esto faltaba
) : ReparacionService {

    override fun crearReparacion(reparacion: Reparacion): Reparacion {
        return reparacionRepository.save(reparacion)
    }

    override fun obtenerTodas(): List<Reparacion> {
        return reparacionRepository.findAll()
    }

    override fun obtenerPorId(id: Long): Reparacion {
        return reparacionRepository.findById(id).orElseThrow {
            EntityNotFoundException("Reparación con ID $id no encontrada")
        }
    }

    override fun actualizarEstado(id: Long, nuevoEstado: String): Reparacion {
        val reparacion = obtenerPorId(id)
        val estado = try {
            EstadoReparacion.valueOf(nuevoEstado.uppercase())
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Estado no válido. Usa: PENDIENTE, REVISION, REPARADO o ENTREGADO")
        }
        val actualizada = reparacion.copy(estado = estado)
        return reparacionRepository.save(actualizada)
    }


    override fun listarPorUsuarioId(id: Long): List<Reparacion> {
        return reparacionRepository.findAll()
            .filter { it.usuario != null && it.usuario.id == id }
    }

    override fun asignarTecnico(reparacionId: Long, tecnicoId: Long): Reparacion {
        val reparacion = obtenerPorId(reparacionId)
        if (reparacion.tecnico?.id == tecnicoId) {
            throw IllegalArgumentException("Este técnico ya está asignado a la reparación")
        }
        val tecnico = usuarioRepository.findById(tecnicoId)
            .orElseThrow { IllegalArgumentException("Técnico no encontrado") }
        if (tecnico.rol != Rol.TECNICO && tecnico.rol != Rol.ADMIN) {
            throw IllegalArgumentException("Este usuario no puede ser técnico")
        }
        val actualizada = reparacion.copy(tecnico = tecnico)
        return reparacionRepository.save(actualizada)
    }


    override fun listarPorTecnicoId(tecnicoId: Long): List<Reparacion> {
        return reparacionRepository.findAll().filter {
            it.tecnico?.id == tecnicoId
        }
    }

    override fun actualizarDiagnostico(id: Long, diagnostico: String, solucion: String, costo: Double): Reparacion {
        val reparacion = obtenerPorId(id)
        val actualizada = reparacion.copy(
            diagnostico = diagnostico,
            solucion = solucion,
            costo = costo,
            estado = EstadoReparacion.REVISION // 👈 Cambio automático de estado
        )
        return reparacionRepository.save(actualizada)
    }

    override fun asignarServicio(reparacionId: Long, servicio: Servicio): Reparacion {
        val reparacion = obtenerPorId(reparacionId)
        val actualizada = reparacion.copy(servicio = servicio)
        return reparacionRepository.save(actualizada)
    }

}
