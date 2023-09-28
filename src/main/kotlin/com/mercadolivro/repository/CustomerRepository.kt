package com.mercadolivro.repository

import com.mercadolivro.model.CustomerModel
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CustomerRepository: CrudRepository<CustomerModel, Int>  {

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): Optional<CustomerModel>


    fun findByNameContaining(name: String):Iterable<CustomerModel>
}