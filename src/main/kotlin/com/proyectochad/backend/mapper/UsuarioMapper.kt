package com.proyectochad.backend.mapper

import com.proyectochad.backend.dto.UsuarioDTO
import com.proyectochad.backend.model.Rol
import com.proyectochad.backend.model.Usuario

object UsuarioMapper {
    fun toDTO(usuario: Usuario): UsuarioDTO = UsuarioDTO(
        id = usuario.id,
        primerNombre = usuario.primerNombre,
        segundoNombre = usuario.segundoNombre,
        primerApellido = usuario.primerApellido,
        segundoApellido = usuario.segundoApellido,
        correo = usuario.correo,
        telefono = usuario.telefono,
        fotoPerfilUrl = usuario.fotoPerfilUrl,
        rol = usuario.rol.name
    )

    fun toEntity(dto: UsuarioDTO): Usuario = Usuario(
        id = dto.id,
        primerNombre = dto.primerNombre,
        segundoNombre = dto.segundoNombre,
        primerApellido = dto.primerApellido,
        segundoApellido = dto.segundoApellido,
        correo = dto.correo,
        contrasena = "", // vacía si no se usa
        telefono = dto.telefono,
        fotoPerfilUrl = dto.fotoPerfilUrl,
        rol = Rol.valueOf(dto.rol)
    )
}

