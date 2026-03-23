package com.bank.customers.infrastructure.adapter.out.db;

import com.bank.customers.application.port.out.CustomerRepositoryPort;
import com.bank.customers.domain.model.Customer;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerJpaAdapter implements CustomerRepositoryPort {

  private final CustomerJpaRepository customerJpaRepository;

  @Override
  public Customer save(Customer customer) {
    CustomerEntity entity = toEntity(customer);
    CustomerEntity savedEntity = customerJpaRepository.save(entity);
    return toDomain(savedEntity);
  }

  @Override
  public List<Customer> findAll() {
    return customerJpaRepository.findAll().stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public boolean existsByEmail(String email) {
    return customerJpaRepository.existsByEmail(email);
  }

  private CustomerEntity toEntity(Customer domain) {
    return CustomerEntity.builder()
        .id(domain.getId())
        .name(domain.getName())
        .email(domain.getEmail())
        .build();
  }

  private Customer toDomain(CustomerEntity entity) {
    return Customer.builder()
        .id(entity.getId())
        .name(entity.getName())
        .email(entity.getEmail())
        .build();
  }
}
