package com.mercadolivro.exception

class AuthenticationException(override var message: String, override var errorCode: String): ApplicationException(message, errorCode) {
}