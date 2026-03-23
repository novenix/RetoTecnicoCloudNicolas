package com.bank.customers.infrastructure.adapter.in.web;

import com.bank.customers.application.port.in.CreateCustomerUseCase;
import com.bank.customers.application.port.in.GetCustomersQuery;
import com.bank.customers.domain.model.Customer;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CreateCustomerUseCase createCustomerUseCase;
  private final GetCustomersQuery getCustomersQuery;

  @PostMapping
  public ResponseEntity<CustomerResponse> createCustomer(
      @Valid @RequestBody CustomerRequest request) {
    Customer customer = Customer.builder().name(request.name()).email(request.email()).build();

    Customer savedCustomer = createCustomerUseCase.createCustomer(customer);
    return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(savedCustomer));
  }

  @GetMapping
  public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
    List<CustomerResponse> responses =
        getCustomersQuery.getAllCustomers().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    return ResponseEntity.ok(responses);
  }

  private CustomerResponse toResponse(Customer domain) {
    return new CustomerResponse(domain.getId(), domain.getName(), domain.getEmail());
  }
}
