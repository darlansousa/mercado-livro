package com.mercadolivro.model

import com.mercadolivro.enums.BookStatusEnum
import com.mercadolivro.enums.BusinessExceptions
import jakarta.persistence.*
import java.math.BigDecimal

@Entity(name = "books")
data class BookModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price: BigDecimal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel? = null
) {
    @Column
    @Enumerated(value = EnumType.STRING)
    var status: BookStatusEnum? = null
        set(value) {
            if (field == BookStatusEnum.DELETED || field == BookStatusEnum.CANCELED) {
                throw BusinessExceptions.INVALID_STATUS_UPDATE.fire()
            }
            field = value
        }

    constructor(
        id: Int? = null,
        name: String,
        price: BigDecimal,
        customer: CustomerModel? = null,
        status: BookStatusEnum?
    ) : this(id, name, price, customer) {
        this.status = status
    }
}