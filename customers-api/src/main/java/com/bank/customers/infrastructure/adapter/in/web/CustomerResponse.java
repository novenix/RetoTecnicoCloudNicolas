package com.bank.customers.infrastructure.adapter.in.web;

public record CustomerResponse(
        Long id,
        String name,
        String email
) {
}
