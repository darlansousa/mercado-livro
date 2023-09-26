package com.mercadolivro.exception

import com.mercadolivro.contoller.response.ErrorResponse
import com.mercadolivro.contoller.response.FieldErrorResponse
import com.mercadolivro.enums.ValidationExceptions
import com.mercadolivro.enums.ValidationExceptions.INVALID_FIELDS
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(BusinessException::class)
    fun handleException(exception: BusinessException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val statusCode = 422
        val error = ErrorResponse(statusCode, exception.message, exception.errorCode, listOf())
        return ResponseEntity(error, HttpStatusCode.valueOf(statusCode))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentValidException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val statusCode = HttpStatus.UNPROCESSABLE_ENTITY.value()
        val error = ErrorResponse(statusCode, INVALID_FIELDS.message, INVALID_FIELDS.code, exception.fieldErrors.map {
            FieldErrorResponse(it.defaultMessage ?: "invalid", it.field)
        })
        return ResponseEntity(error, HttpStatusCode.valueOf(statusCode))
    }
}