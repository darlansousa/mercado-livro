package com.mercadolivro.contoller

import com.mercadolivro.contoller.request.PostCustomerRequest
import com.mercadolivro.contoller.request.PutCustomerRequest
import com.mercadolivro.contoller.response.CustomerResponse
import com.mercadolivro.extension.toModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.service.CustomerService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("customers")
@Validated
class CustomerController(
    private val customerService: CustomerService) {

    @GetMapping
    fun getAll(@RequestParam name: String?): ResponseEntity<Iterable<CustomerResponse>> {
        return ResponseEntity.ok(customerService.getAll(name)
            .map { CustomerResponse(it.id, it.name, it.email, it.status.name) })
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid customerPost: PostCustomerRequest): ResponseEntity<CustomerResponse> {
        return ResponseEntity.ok(CustomerResponse.of(customerService.create(customerPost.toModel())))
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id:Int): ResponseEntity<CustomerResponse> {
        return ResponseEntity.ok(CustomerResponse.of(customerService.getById(id)))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody @Valid customerPut: PutCustomerRequest): ResponseEntity<Void> {
        customerService.update(id, customerPut.name, customerPut.email)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        customerService.delete(id)
        return ResponseEntity.ok().build()
    }
}