package com.proyectochad.backend.mapper

import com.proyectochad.backend.dto.UsuarioDTO
import com.proyectochad.backend.model.Rol
import com.proyectochad.backend.model.Usuario

object UsuarioMapper {
    fun toDTO(usuario: Usuario): UsuarioDTO = UsuarioDTO(
        id = usuario.id,
        nombre = usuario.nombre,
        correo = usuario.correo,
        rol = usuario.rol.name
    )

    fun toEntity(dto: UsuarioDTO): Usuario = Usuario(
        id = dto.id,
        nombre = dto.nombre,
        correo = dto.correo,
        contrasena = "", // dejar vacía si no se usa en el contexto
        rol = Rol.valueOf(dto.rol)
    )
}
