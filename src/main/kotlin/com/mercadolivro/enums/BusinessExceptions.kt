package com.mercadolivro.enums

import com.mercadolivro.exception.BusinessException

enum class BusinessExceptions(val code: String, val message: String) {
    BOOK_NOT_FOUND("MLBE-1001", "Book not found") {
        override fun fire(): BusinessException {
            return BusinessException(message, code)
        }
                                                },
    CUSTOMER_NOT_FOUND("MLBE-2001", "Customer not found") {
        override fun fire(): BusinessException {
            return BusinessException(message, code)
        }
    },
    INVALID_STATUS_UPDATE("MLBE-2002", "It is not possible update book status") {
        override fun fire(): BusinessException {
            return BusinessException(message, code)
        }
    },
    NO_AVAILABLE_FOR_SALE("MLBE-2003", "Books is not available for sale") {
        override fun fire(): BusinessException {
            return BusinessException(message, code)
        }
    };

    abstract fun fire(): BusinessException
}