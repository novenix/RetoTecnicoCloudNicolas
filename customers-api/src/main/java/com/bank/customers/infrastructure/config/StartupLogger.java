package com.bank.customers.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartupLogger {

  @Value("${app.startup.message}")
  private String startupMessage;

  @EventListener(ApplicationReadyEvent.class)
  public void logStartupMessage() {
    log.info("***************************************************");
    log.info(">>>>> STATUS: " + startupMessage);
    log.info("***************************************************");
  }
}
