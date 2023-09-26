package com.mercadolivro.service

import com.mercadolivro.enums.BookStatusEnum.*
import com.mercadolivro.enums.BusinessExceptions.BOOK_NOT_FOUND
import com.mercadolivro.model.BookModel
import com.mercadolivro.repository.BookRepository
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BookService(private val repo: BookRepository,
                  @Lazy private val purchaseService: PurchaseService,
                  private val customerService: CustomerService) {

    fun create(book: BookModel, customerId: Int): BookModel {
        val customer = customerService.getById(customerId)
        book.customer = customer
        return repo.save(book)
    }

    fun getAll(): Iterable<BookModel> = repo.findAll()

    fun getAll(pageable: Pageable): Page<BookModel> = repo.findAll(pageable)

    fun getAllActive(): Iterable<BookModel> = repo.findByStatus(ACTIVE)

    fun getAllActive(pageable: Pageable): Page<BookModel> = repo.findByStatus(ACTIVE, pageable)

    fun getPublishedBy(customerId: Int, pageable: Pageable): Page<BookModel> =
        repo.findByCustomer_id(customerId, pageable)

    fun getSoldBy(customerId: Int, pageable: Pageable): List<BookModel> =
        purchaseService.getAllBooksIdsByCustomerId(customerId).flatMap { listOf(getById(it)) }

    fun getById(id: Int): BookModel = repo.findById(id).orElseThrow { BOOK_NOT_FOUND.fire()}

    fun delete(id: Int) {
        val book = getById(id)
        book.status = CANCELED
        repo.save(book)
    }

    fun update(id: Int, name: String, price: BigDecimal): BookModel {
        val book = getById(id)
        book.name = name
        book.price = price
        return update(book)
    }

    fun update(book: BookModel): BookModel = repo.save(book)

    fun purchase(books: MutableList<BookModel>) {
        books.map { it.status = SOLD}
        repo.saveAll(books)
    }


    fun deleteByCustomerId(id: Int) {
        repo.findByCustomerId(id).forEach{
            it.status = DELETED
            repo.save(it)
        }
    }

    fun getByIds(ids: Set<Int>): List<BookModel> {
        return repo.findAllById(ids)
    }

    fun isBooksAvailableForSale(books: List<BookModel>): Boolean {
        return books.all { it.status == ACTIVE }
    }

}