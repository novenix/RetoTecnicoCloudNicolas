package com.bank.customers.infrastructure.adapter.in.web;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

  private final Bucket bucket;

  public RateLimitInterceptor() {
    // Definir límite: 10 peticiones por minuto
    Bandwidth limit = Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1)));
    this.bucket = Bucket.builder().addLimit(limit).build();
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
    if (probe.isConsumed()) {
      response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
      return true;
    } else {
      long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
      response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
      response.sendError(
          HttpStatus.TOO_MANY_REQUESTS.value(), "Has excedido el límite de peticiones");
      return false;
    }
  }
}
