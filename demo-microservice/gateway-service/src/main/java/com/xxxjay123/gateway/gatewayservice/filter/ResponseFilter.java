package com.xxxjay123.gateway.gatewayservice.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
public class ResponseFilter {


  @Autowired
  FilterUtils filterUtils;

  @Bean
  GlobalFilter postGlobalFilter() {
    return (exchange, chain) -> {
      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        String correlationId = filterUtils.getCorrelationId(requestHeaders);

        exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID,
            correlationId);
      }));
    };
  }
}
