package com.mercadolivro.repository

import com.mercadolivro.enums.BookStatusEnum
import com.mercadolivro.model.BookModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BookRepository: JpaRepository<BookModel, Int> {
    fun findByStatus(status: BookStatusEnum): Iterable<BookModel>
    fun findByStatus(status: BookStatusEnum, pageable: Pageable): Page<BookModel>

    fun findByCustomer_id(id: Int, pageable: Pageable): Page<BookModel>

    fun findByCustomerId(id: Int): Iterable<BookModel>
}