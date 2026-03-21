package com.bank.customers.application.port.in;

import com.bank.customers.domain.model.Customer;
import java.util.List;

public interface GetCustomersQuery {
    List<Customer> getAllCustomers();
}
