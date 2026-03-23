package com.bank.customers.application.port.out;

import com.bank.customers.domain.model.Customer;
import java.util.List;

public interface CustomerRepositoryPort {
  Customer save(Customer customer);
  List<Customer> findAll();
  boolean existsByEmail(String email);
  }
