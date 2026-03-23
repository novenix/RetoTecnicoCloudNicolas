package com.bank.customers.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bank.customers.infrastructure.adapter.in.web.CustomerRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev") // Usamos el perfil dev que tiene H2 configurado
class CustomerIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  @DisplayName("Flujo Completo: Crear cliente y recuperarlo satisfactoriamente")
  void fullCustomerFlow_Success() throws Exception {
    // 1. Preparar el cliente
    CustomerRequest request = new CustomerRequest("Integración Test", "test@integracion.com");

    // 2. Ejecutar POST para crear el cliente
    mockMvc
        .perform(
            post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("Integración Test"))
        .andExpect(jsonPath("$.email").value("test@integracion.com"));

    // 3. Ejecutar GET para verificar que el cliente persiste en la base de datos H2
    mockMvc
        .perform(get("/api/customers"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].name").value("Integración Test"));
  }
}
