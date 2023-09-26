package com.mercadolivro.contoller.request

import com.mercadolivro.validation.EmailAvailable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class PostCustomerRequest(
    @field:NotEmpty
    var name: String,
    @field:Email
    @field:EmailAvailable
    var email: String,
    @field:NotEmpty
    var password: String
)