package com.mercadolivro.service

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Roles
import com.mercadolivro.exception.BusinessException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import io.mockk.called
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    @InjectMockKs
    private lateinit var subject: CustomerService

    @MockK
    private lateinit var customerRepositoryMock: CustomerRepository
    @MockK
    private lateinit var bookServiceMock: BookService


    @Test
    fun `should return all customers`() {
        val mockCustomers =  listOf(buildCustomer(), buildCustomer())
        every { customerRepositoryMock.findAll() } returns mockCustomers
        val result = subject.getAll(null)
        assertEquals(mockCustomers, result)
        verify(exactly = 1) { customerRepositoryMock.findAll() }
        verify(exactly = 0) { customerRepositoryMock.findByNameContaining(any()) }
        verify { bookServiceMock wasNot called }
    }

    @Test
    fun `should return empty customers when name is informed`() {
        val name = Math.random().toString()
        every { customerRepositoryMock.findByNameContaining(name) } returns listOf()
        val result = subject.getAll(name)
        assertEquals(listOf<CustomerModel>(), result)
        verify(exactly = 0) { customerRepositoryMock.findAll() }
        verify(exactly = 1) { customerRepositoryMock.findByNameContaining(name) }
        verify { bookServiceMock wasNot called }
    }

    @Test
    fun `should return filtered customers when name is informed`() {
        val name = "name"
        val mockCustomers =  listOf(buildCustomer(), buildCustomer())
        every { customerRepositoryMock.findByNameContaining(name) } returns mockCustomers
        val result = subject.getAll(name)
        assertEquals(mockCustomers, result)
        verify(exactly = 0) { customerRepositoryMock.findAll() }
        verify(exactly = 1) { customerRepositoryMock.findByNameContaining(name) }
        verify { bookServiceMock wasNot called }
    }

    @Test
    fun `should create customer and set Customer Role`() {
        val email = "dk@gmail.com"
        val mockCustomer = buildCustomerWithNoRoles(email = email)
        val expected = buildCustomer(email = email)
        every { customerRepositoryMock.save(expected) } returns expected
        val result = subject.create(mockCustomer)
        assertEquals(expected, result)
        verify(exactly = 1) { customerRepositoryMock.save(expected) }
        verify { bookServiceMock wasNot called }
    }

    @Test
    fun `should find customer by informed id`() {
        val id = Random().nextInt()
        val mockCustomer = buildCustomer()
        every { customerRepositoryMock.findById(id) } returns Optional.of(mockCustomer)
        val result = subject.getById(id)
        assertEquals(mockCustomer, result)
        verify(exactly = 1) { customerRepositoryMock.findById(id) }
        verify { bookServiceMock wasNot called }
    }

    @Test
    fun `should throw error when customer not found`() {
        val id = Random().nextInt()
        val mockCustomer = buildCustomer()
        every { customerRepositoryMock.findById(id) } returns Optional.empty()

        val error = org.junit.jupiter.api.assertThrows<BusinessException> {
            subject.getById(id)
        }
        assertEquals(error.message, "Customer not found")
        assertEquals(error.errorCode, "MLBE-2001")
        verify(exactly = 1) { customerRepositoryMock.findById(id) }
        verify { bookServiceMock wasNot called }
    }

    private fun buildCustomer(
        id: Int? = null,
        name: String = "customer name",
        email: String = "${UUID.randomUUID()}",
        password: String = "password"
    ) = CustomerModel(
        id = id,
        name = name,
        email = email,
        password = password,
        status = CustomerStatus.ACTIVE,
        roles = setOf(Roles.CUSTOMER)
        )

    private fun buildCustomerWithNoRoles(
        id: Int? = null,
        name: String = "customer name",
        email: String = "${UUID.randomUUID()}",
        password: String = "password"
    ) = CustomerModel(
        id = id,
        name = name,
        email = email,
        password = password,
        status = CustomerStatus.ACTIVE,
        roles = setOf()
    )



}