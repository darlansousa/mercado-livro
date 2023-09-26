package com.mercadolivro.contoller.response

import com.fasterxml.jackson.annotation.JsonAlias
import com.mercadolivro.enums.BookStatusEnum
import com.mercadolivro.model.BookModel
import java.math.BigDecimal

data class BookResponse(
    var id: Int?,
    var name: String,
    var price: BigDecimal,
    var status: BookStatusEnum?,
    @JsonAlias("customer_id")
    var customerId: Int?) {
    companion object {
        fun of(book: BookModel): BookResponse {
            return BookResponse(book.id, book.name, book.price, book.status, book.customer?.id)
        }
    }
}


