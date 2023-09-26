package com.mercadolivro.contoller

import com.mercadolivro.contoller.request.PostBookRequest
import com.mercadolivro.contoller.request.PutBookRequest
import com.mercadolivro.contoller.response.BookResponse
import com.mercadolivro.extension.toModel
import com.mercadolivro.service.BookService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("books")
class BookController(var service: BookService) {

    @Operation(summary = "Create books", description = "Returns 201 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "202", description = "Successful Operation"),
            ApiResponse(responseCode = "404", description = "Such a car does not exist"),
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid bookPost: PostBookRequest): ResponseEntity<BookResponse> {
        val newBook = service.create(bookPost.toModel(), bookPost.customerId)
        return ResponseEntity.ok(BookResponse.of(newBook))
    }

    @PatchMapping("/{id}")
    fun update(@PathVariable id:Int, @RequestBody @Valid putBook: PutBookRequest): ResponseEntity<BookResponse> {
        return ResponseEntity.ok(BookResponse.of(service.update(id, putBook.name, putBook.price)))
    }
    @GetMapping
    fun findAll(@PageableDefault(page = 0, size = 10) pageable: Pageable): ResponseEntity<Iterable<BookResponse>> {
        return ResponseEntity.ok(service.getAll(pageable).map { BookResponse.of(it) })
    }

    @GetMapping("/active")
    fun findAllActives(@PageableDefault(page = 0, size = 10) pageable: Pageable): ResponseEntity<Page<BookResponse>> =
        ResponseEntity.ok(service.getAllActive(pageable).map { BookResponse.of(it) })

    @GetMapping("/sold-by-customer/{id}")
    fun findAllSoldBy(@PathVariable id: Int, @PageableDefault(page = 0, size = 10) pageable: Pageable): ResponseEntity<List<BookResponse>> =
        ResponseEntity.ok(service.getSoldBy(id, pageable).map { BookResponse.of(it) })

    @GetMapping("/published-by-customer/{id}")
    fun findAllPublishedBy(@PathVariable id: Int, @PageableDefault(page = 0, size = 10) pageable: Pageable): ResponseEntity<Page<BookResponse>> =
        ResponseEntity.ok(service.getPublishedBy(id, pageable).map { BookResponse.of(it) })

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<BookResponse> =
        ResponseEntity.ok(BookResponse.of(service.getById(id)))

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Int): ResponseEntity<BookResponse> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }

}