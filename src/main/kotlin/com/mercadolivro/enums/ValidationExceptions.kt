package com.mercadolivro.enums

import com.mercadolivro.exception.ValidationException

enum class ValidationExceptions(val code: String, val message: String) {
    INVALID_FIELDS("MLVE-0001", "Invalid fields"){
        override fun fire(): ValidationException {
            return ValidationException(message, code)
        }
    };

    abstract fun fire(): ValidationException
}