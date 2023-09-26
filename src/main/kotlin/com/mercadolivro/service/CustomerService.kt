package com.mercadolivro.service

import com.mercadolivro.enums.BusinessExceptions
import com.mercadolivro.enums.BusinessExceptions.CUSTOMER_NOT_FOUND
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Profile
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.context.annotation.Lazy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomerService(
    val repo: CustomerRepository,
    @Lazy val bookService: BookService,
    private val bCrypt: BCryptPasswordEncoder) {

    fun getAll(name: String?): Iterable<CustomerModel> {
        name?.let { return repo.findByNameContaining(it) }
        return repo.findAll()
    }

    fun getById(id: Int): CustomerModel {
        return repo.findById(id).orElseThrow{ CUSTOMER_NOT_FOUND.fire() }
    }

    fun create(customer: CustomerModel): CustomerModel {
        val copyCustomer = customer.copy(
            roles = setOf(Profile.CUSTOMER),
            password = bCrypt.encode(customer.password)
        )
        return repo.save(copyCustomer)
    }

    fun update(id: Int, customer: CustomerModel) {
        if(repo.existsById(id)) {
            customer.id = id
            repo.save(customer)
        }
        throw CUSTOMER_NOT_FOUND.fire()
    }

    fun update(id: Int, name: String, email: String) {
        val savedCustomer = getById(id)
        savedCustomer.name = name
        savedCustomer.email = email
        repo.save(savedCustomer)
    }

    fun delete(id: Int) {
        val customer = getById(id)
        bookService.deleteByCustomerId(id)
        customer.status = CustomerStatus.INACTIVE
        repo.save(customer)
    }

    fun emailAvailable(email: String?): Boolean {
        if(!email.isNullOrEmpty()) {
            return !repo.existsByEmail(email)
        }
       return false
    }
}