package com.bank.customers.application.port.in;

import com.bank.customers.domain.model.Customer;

public interface CreateCustomerUseCase {
  Customer createCustomer(Customer customer);
}
