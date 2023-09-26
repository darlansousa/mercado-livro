package com.mercadolivro.repository

import com.mercadolivro.model.CustomerModel
import org.springframework.data.repository.CrudRepository

interface CustomerRepository: CrudRepository<CustomerModel, Int>  {

    fun existsByEmail(email: String): Boolean

    fun findByNameContaining(name: String):Iterable<CustomerModel>
}