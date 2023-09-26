package com.mercadolivro.exception

class ValidationException(override var message: String, override var errorCode: String): ApplicationException(message, errorCode) {
}