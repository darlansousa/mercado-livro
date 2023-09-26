package com.mercadolivro.model

import com.mercadolivro.enums.CustomerStatus
import jakarta.persistence.*

@Entity(name = "customers")
data class CustomerModel (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var email: String,

    @Column
    @Enumerated(value = EnumType.STRING)
    var status: CustomerStatus,

    @Column
    val password: String
)