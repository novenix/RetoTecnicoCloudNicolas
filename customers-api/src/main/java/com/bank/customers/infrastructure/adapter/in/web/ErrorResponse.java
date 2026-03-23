package com.bank.customers.infrastructure.adapter.in.web;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
    String message,
    int status,
    LocalDateTime timestamp,
    Map<String, String> details
) {}
