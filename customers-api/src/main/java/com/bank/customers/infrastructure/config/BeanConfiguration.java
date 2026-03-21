package com.bank.customers.infrastructure.config;

import com.bank.customers.application.port.out.CustomerRepositoryPort;
import com.bank.customers.application.service.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CustomerService customerService(CustomerRepositoryPort customerRepositoryPort) {
        return new CustomerService(customerRepositoryPort);
    }
}
