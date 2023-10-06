package com.mercadolivro.repository

import com.mercadolivro.helper.buildCustomer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.Optional


@SpringBootTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    private lateinit var subject: CustomerRepository

    @BeforeEach
    fun setup() {
        subject.deleteAll()
    }

    @Test
    fun `should return customers containing name`() {
        val mike = subject.save(buildCustomer(name = "Mike"))
        val michel = subject.save(buildCustomer(name = "Michel"))
        subject.save(buildCustomer(name = "Nataly"))
        val result = subject.findByNameContaining("Mi")
        assertEquals(2, result.count())
        assertEquals(listOf(mike, michel), result)
    }

    @Test
    fun `should return true for existent email`() {
        val mike = subject.save(buildCustomer(email = "mike@fake.com"))
        assertTrue(subject.existsByEmail("mike@fake.com"))
        assertFalse(subject.existsByEmail("michel@fake.com"))
    }

    @Test
    fun `should return customer by email`() {
        val expected = subject.save(buildCustomer(email = "mike@fake.com"))
        assertEquals(Optional.of(expected), subject.findByEmail("mike@fake.com"))
        assertTrue(subject.findByEmail("michel@fake.com").isEmpty)
    }

}