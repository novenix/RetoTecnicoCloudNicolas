package com.bank.customers.infrastructure.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bank.customers.application.port.in.CreateCustomerUseCase;
import com.bank.customers.application.port.in.GetCustomersQuery;
import com.bank.customers.domain.exception.BusinessException;
import com.bank.customers.domain.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private CreateCustomerUseCase createCustomerUseCase;
  @MockBean private GetCustomersQuery getCustomersQuery;

  @Test
  @DisplayName("POST /api/customers - Debe retornar 201 cuando es válido")
  void createCustomer_Success() throws Exception {
    CustomerRequest request = new CustomerRequest("Juan", "juan@email.com");
    Customer saved = new Customer(1L, "Juan", "juan@email.com");
    when(createCustomerUseCase.createCustomer(any(Customer.class))).thenReturn(saved);

    mockMvc
        .perform(
            post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  @DisplayName("POST /api/customers - Debe retornar 409 cuando el email ya existe")
  void createCustomer_Conflict() throws Exception {
    CustomerRequest request = new CustomerRequest("Juan", "juan@email.com");
    when(createCustomerUseCase.createCustomer(any(Customer.class)))
        .thenThrow(new BusinessException("El email 'juan@email.com' ya está registrado"));

    mockMvc
        .perform(
            post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.message").value("El email 'juan@email.com' ya está registrado"))
        .andExpect(jsonPath("$.status").value(409));
  }

  @Test
  @DisplayName("GET /api/customers - Debe retornar lista")
  void getAll_Success() throws Exception {
    when(getCustomersQuery.getAllCustomers()).thenReturn(List.of());
    mockMvc.perform(get("/api/customers")).andExpect(status().isOk());
  }
}
