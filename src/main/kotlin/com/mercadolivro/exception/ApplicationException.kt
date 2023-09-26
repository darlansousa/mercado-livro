package com.mercadolivro.exception

open class ApplicationException(override val message: String, open val errorCode: String): Exception() {
}