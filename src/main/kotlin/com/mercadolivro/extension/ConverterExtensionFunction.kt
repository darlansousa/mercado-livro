package com.mercadolivro.extension

import com.mercadolivro.contoller.request.PostBookRequest
import com.mercadolivro.contoller.request.PostCustomerRequest
import com.mercadolivro.contoller.request.PutBookRequest
import com.mercadolivro.contoller.request.PutCustomerRequest
import com.mercadolivro.contoller.response.BookResponse
import com.mercadolivro.enums.BookStatusEnum
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel

fun PostCustomerRequest.toModel(): CustomerModel {
    return CustomerModel(
        name = this.name,
        email = this.email,
        status = CustomerStatus.ACTIVE,
        password = this.password)
}

fun PostBookRequest.toModel(): BookModel {
    return BookModel(
        name = this.name,
        price = this.price,
        status = BookStatusEnum.ACTIVE)
}
