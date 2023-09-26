package com.mercadolivro.contoller.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class PostBookRequest(
    @field:NotEmpty
    var name: String,
    @field:Positive
    var price: BigDecimal,
    @JsonAlias("customer_id")
    @field:NotNull
    var customerId: Int
)