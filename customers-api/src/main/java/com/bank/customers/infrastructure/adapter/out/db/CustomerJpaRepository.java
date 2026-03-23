package com.bank.customers.infrastructure.adapter.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {
  boolean existsByEmail(String email);
}
