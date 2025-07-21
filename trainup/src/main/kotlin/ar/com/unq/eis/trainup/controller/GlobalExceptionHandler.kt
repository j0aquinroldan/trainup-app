package ar.com.unq.eis.trainup.controller

import ar.com.unq.eis.trainup.controller.Exceptions.RutinaNoSeguidaException
import ar.com.unq.eis.trainup.controller.Exceptions.UsuarioDuplicadoException
import ar.com.unq.eis.trainup.controller.dto.ErrorDTO
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@RestController
@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handlerIllegallArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorDTO> {
        val response = ErrorDTO(
            error = "Invalid data",
            message = e.message?: "The data provided is invalid"
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(DataAccessException::class)
    fun handleDatabaseException(e: DataAccessException): ResponseEntity<ErrorDTO> {
        val response = ErrorDTO(
            error = "Database error",
            message = "There was an error while accessing the database. Try again later"
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFoundException(e: NoSuchElementException): ResponseEntity<ErrorDTO> {
        val response = ErrorDTO(
            error = "Not found",
            message = e.message ?: "The requested resource was not found"
        )
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(UsuarioDuplicadoException::class)
    fun handleUsuarioDuplicadoException(e: UsuarioDuplicadoException): ResponseEntity<ErrorDTO> {
        val response = ErrorDTO(
            error = "Duplicate key",
            message = e.message!!
        )
        return ResponseEntity(response, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(RutinaNoSeguidaException::class)
    fun handleRutinaNoSeguidaException(e: RutinaNoSeguidaException): ResponseEntity<ErrorDTO> {
        val response = ErrorDTO(
            error = "Rutina no seguida",
            message = e.message!!
        )
        return ResponseEntity(response, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(e: Exception): ResponseEntity<ErrorDTO> {
        val response = ErrorDTO(
            error = "Internal server error",
            message = e.message!!
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}