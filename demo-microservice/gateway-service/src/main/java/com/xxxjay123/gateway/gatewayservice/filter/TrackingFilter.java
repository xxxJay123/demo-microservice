package com.xxxjay123.gateway.gatewayservice.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class TrackingFilter implements GlobalFilter {
  @Autowired
  FilterUtils filterUtils;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange,
      GatewayFilterChain chain) {
    HttpHeaders requestHeader = exchange.getRequest().getHeaders(); 

    if (isCorrelationIdPresent(requestHeader)) {

    } else {
      String correlationId = generateCorrelationId(); 
      exchange = filterUtils.setCorrelationId(exchange, correlationId);
    }
    return chain.filter(exchange);
  }


  private boolean isCorrelationIdPresent(HttpHeaders header) {
    return filterUtils.getCorrelationId(header) != null;
  }

  private String generateCorrelationId() {
    return UUID.randomUUID().toString();
  }
}
