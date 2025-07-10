package com.proyectochad.backend.controller

import com.proyectochad.backend.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@RestController
@RequestMapping("/api/usuarios")
class UsuarioFotoController(
    private val usuarioRepository: UsuarioRepository
) {

    @Value("\${server.port}")
    private val port: String = "8080"

    @PostMapping("/{id}/foto")
    fun subirFoto(
        @PathVariable id: Long,
        @RequestParam("archivo") archivo: MultipartFile
    ): ResponseEntity<String> {
        val usuario = usuarioRepository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()

        // Ruta absoluta segura y multiplataforma
        val uploadDir = File(System.getProperty("user.dir"), "uploads")
        if (!uploadDir.exists()) uploadDir.mkdirs()

        val extension = archivo.originalFilename?.substringAfterLast('.') ?: "jpg"
        val nombreArchivo = "foto_${UUID.randomUUID()}.$extension"
        val rutaArchivo = File(uploadDir, nombreArchivo)

        archivo.transferTo(rutaArchivo)

        val url = "http://localhost:$port/$nombreArchivo"
        val usuarioActualizado = usuario.copy(fotoPerfilUrl = url)

        usuarioRepository.save(usuarioActualizado)

        return ResponseEntity.ok("\"$url\"")
    }

}
