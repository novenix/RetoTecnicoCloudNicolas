package com.bank.customers.infrastructure.adapter.in.web;

import com.bank.customers.application.port.in.CreateCustomerUseCase;
import com.bank.customers.application.port.in.GetCustomersQuery;
import com.bank.customers.domain.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateCustomerUseCase createCustomerUseCase;

    @MockBean
    private GetCustomersQuery getCustomersQuery;

    @Test
    @DisplayName("POST /api/customers - Debe retornar 201 cuando el cliente es válido")
    void createCustomer_ValidRequest_ReturnsCreated() throws Exception {
        // Arrange
        CustomerRequest request = new CustomerRequest("Juan Perez", "juan@email.com");
        Customer savedCustomer = new Customer(1L, "Juan Perez", "juan@email.com");

        when(createCustomerUseCase.createCustomer(any(Customer.class))).thenReturn(savedCustomer);

        // Act & Assert
        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Juan Perez"))
                .andExpect(jsonPath("$.email").value("juan@email.com"));
    }

    @Test
    @DisplayName("POST /api/customers - Debe retornar 400 cuando el email es inválido")
    void createCustomer_InvalidEmail_ReturnsBadRequest() throws Exception {
        // Arrange
        CustomerRequest request = new CustomerRequest("Juan Perez", "email-invalido");

        // Act & Assert
        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/customers - Debe retornar lista de clientes y status 200")
    void getAllCustomers_ReturnsList() throws Exception {
        // Arrange
        List<Customer> customers = List.of(
                new Customer(1L, "Juan", "juan@email.com")
        );
        when(getCustomersQuery.getAllCustomers()).thenReturn(customers);

        // Act & Assert
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Juan"));
    }
}
