package com.mercadolivro.contoller.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

class PutBookRequest(
    @field:NotEmpty
    var name: String,
    @field:Positive
    var price: BigDecimal
) {
}