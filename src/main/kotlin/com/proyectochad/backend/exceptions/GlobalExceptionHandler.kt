package com.proyectochad.backend.exceptions

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleNotFound(ex: EntityNotFoundException): ResponseEntity<ApiError> {
        val error = ApiError(HttpStatus.NOT_FOUND, ex.message ?: "Entidad no encontrada")
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(ex: IllegalArgumentException): ResponseEntity<ApiError> {
        val error = ApiError(HttpStatus.BAD_REQUEST, ex.message ?: "Parámetros inválidos")
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneral(ex: Exception): ResponseEntity<ApiError> {
        val error = ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado: ${ex.localizedMessage}")
        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

data class ApiError(val status: HttpStatus, val message: String)
