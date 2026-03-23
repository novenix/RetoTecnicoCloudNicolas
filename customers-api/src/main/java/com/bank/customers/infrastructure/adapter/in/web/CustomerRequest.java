package com.bank.customers.infrastructure.adapter.in.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
    @NotBlank(message = "El nombre no puede estar vacío") String name,
    @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El formato del email es inválido")
        String email) {}
