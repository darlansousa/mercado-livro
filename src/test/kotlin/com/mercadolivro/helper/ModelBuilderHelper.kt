package com.mercadolivro.helper

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Roles
import com.mercadolivro.model.CustomerModel
import java.util.*

fun buildCustomer(
    id: Int? = null,
    name: String = "customer name",
    email: String = "${UUID.randomUUID()}",
    password: String = "password"
) = CustomerModel(
    id = id,
    name = name,
    email = email,
    password = password,
    status = CustomerStatus.ACTIVE,
    roles = setOf(Roles.CUSTOMER)
)

fun buildCustomerWithNoRoles(
    id: Int? = null,
    name: String = "customer name",
    email: String = "${UUID.randomUUID()}",
    password: String = "password"
) = CustomerModel(
    id = id,
    name = name,
    email = email,
    password = password,
    status = CustomerStatus.ACTIVE,
    roles = setOf()
)