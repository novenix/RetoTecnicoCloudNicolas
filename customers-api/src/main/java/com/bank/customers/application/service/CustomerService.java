package com.bank.customers.application.service;

import java.util.List;

import com.bank.customers.application.port.in.CreateCustomerUseCase;
import com.bank.customers.application.port.in.GetCustomersQuery;
import com.bank.customers.application.port.out.CustomerRepositoryPort;
import com.bank.customers.domain.model.Customer;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerService implements CreateCustomerUseCase, GetCustomersQuery {

  private final CustomerRepositoryPort customerRepositoryPort;

  @Override
  public Customer createCustomer(Customer customer) {
    // Aquí podríamos añadir lógica de negocio, por ejemplo validaciones adicionales
    return customerRepositoryPort.save(customer);
  }

  @Override
  public List<Customer> getAllCustomers() {
    return customerRepositoryPort.findAll();
  }
}
