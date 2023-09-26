package com.mercadolivro.exception

class BusinessException(override var message: String, override var errorCode: String): ApplicationException(message, errorCode) {
}