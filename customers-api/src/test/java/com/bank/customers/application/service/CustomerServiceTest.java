package com.bank.customers.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.bank.customers.application.port.out.CustomerRepositoryPort;
import com.bank.customers.domain.exception.BusinessException;
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
  @DisplayName("Debe crear un cliente correctamente cuando no existe el email")
  void createCustomer_Success() {
    Customer customer = Customer.builder().name("Nicolas").email("n@mail.com").build();
    when(customerRepositoryPort.existsByEmail(anyString())).thenReturn(false);
    when(customerRepositoryPort.save(any(Customer.class))).thenReturn(customer);

    Customer result = customerService.createCustomer(customer);

    assertNotNull(result);
    verify(customerRepositoryPort).save(customer);
  }

  @Test
  @DisplayName("Debe lanzar BusinessException cuando el email ya existe")
  void createCustomer_DuplicateEmail_ThrowsException() {
    Customer customer = Customer.builder().name("Nicolas").email("n@mail.com").build();
    when(customerRepositoryPort.existsByEmail("n@mail.com")).thenReturn(true);

    assertThrows(BusinessException.class, () -> customerService.createCustomer(customer));
    verify(customerRepositoryPort, never()).save(any());
  }

  @Test
  @DisplayName("Debe retornar la lista de clientes")
  void getAllCustomers_Success() {
    when(customerRepositoryPort.findAll()).thenReturn(List.of());
    List<Customer> result = customerService.getAllCustomers();
    assertNotNull(result);
    verify(customerRepositoryPort).findAll();
  }
}
