package com.mercadolivro.mapper

import com.mercadolivro.contoller.request.PostPurchaseRequest
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper(private val bookService: BookService, private val customerService: CustomerService) {

    fun toModel(post: PostPurchaseRequest): PurchaseModel {
        val customer = customerService.getById(post.customerId)
        val books = bookService.getByIds(post.bookIds)
        val totalPrice = books.sumOf { it.price }
        return PurchaseModel(customer = customer, books = books.toMutableList(), price = totalPrice)
    }
}