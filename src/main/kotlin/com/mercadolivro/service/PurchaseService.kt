package com.mercadolivro.service

import com.mercadolivro.enums.BusinessExceptions
import com.mercadolivro.enums.BusinessExceptions.NO_AVAILABLE_FOR_SALE
import com.mercadolivro.events.PurchaseEvent
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val repo:PurchaseRepository,
    private val bookService: BookService,
    private val eventPublisher: ApplicationEventPublisher) {

    fun getAllBooksIdsByCustomerId(id: Int): List<Int> {
        return repo.findByCustomer_id(id)
            .flatMap { purchase -> purchase.books
                .map { it.id?: 0 } }.filter { it != 0 }
    }

    fun create(purchase: PurchaseModel) {
        if(bookService.isBooksAvailableForSale(purchase.books)) {
            repo.save(purchase)
            eventPublisher.publishEvent(PurchaseEvent(this, purchase))
        }
        throw NO_AVAILABLE_FOR_SALE.fire()
    }

    fun update(purchaseModel: PurchaseModel) {
        repo.save(purchaseModel)
    }
}