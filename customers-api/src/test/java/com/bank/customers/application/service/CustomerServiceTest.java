package com.bank.customers.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.bank.customers.application.port.out.CustomerRepositoryPort;
import com.bank.customers.domain.model.Customer;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  @Mock private CustomerRepositoryPort customerRepositoryPort;

  @InjectMocks private CustomerService customerService;

  @Test
  @DisplayName("Debe crear un cliente correctamente llamando al puerto de salida")
  void createCustomer_Success() {
    // Arrange (Preparación)
    Customer customerToSave =
        Customer.builder().name("Nicolas Torres").email("nicolas@email.com").build();

    Customer savedCustomer =
        Customer.builder().id(1L).name("Nicolas Torres").email("nicolas@email.com").build();

    when(customerRepositoryPort.save(any(Customer.class))).thenReturn(savedCustomer);

    // Act (Ejecución)
    Customer result = customerService.createCustomer(customerToSave);

    // Assert (Verificación)
    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("Nicolas Torres", result.getName());
    verify(customerRepositoryPort, times(1)).save(customerToSave);
  }

  @Test
  @DisplayName("Debe retornar la lista de clientes desde el repositorio")
  void getAllCustomers_Success() {
    // Arrange
    List<Customer> expectedList =
        List.of(
            Customer.builder().id(1L).name("Juan").build(),
            Customer.builder().id(2L).name("Maria").build());

    when(customerRepositoryPort.findAll()).thenReturn(expectedList);

    // Act
    List<Customer> result = customerService.getAllCustomers();

    // Assert
    assertEquals(2, result.size());
    assertEquals("Juan", result.get(0).getName());
    verify(customerRepositoryPort, times(1)).findAll();
  }
}
