package com.mercadolivro.contoller.response

import com.mercadolivro.model.CustomerModel

data class CustomerResponse(
    var id: Int?,
    var name: String,
    var email: String,
    var status: String
) {
    companion object {
        fun of(customer: CustomerModel): CustomerResponse {
            return CustomerResponse(customer.id, customer.name, customer.email, customer.status.name)
        }
    }
}