package com.xxxjay123.ldap.ldapservice.config;

import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  public static final String BASIC_AUTH_SECURITY_SCHEME = "basicAuth";

  @Value("${spring.application.name}")
  private String applicationName;
  
  @Bean
     OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(BASIC_AUTH_SECURITY_SCHEME,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")))
                .info(new Info().title("Your API Title"));
    }
  @Bean
  public GroupedOpenApi customApi() {
      return GroupedOpenApi.builder().group("api").pathsToMatch("/api/**").build();
  }

  @Bean
  public GroupedOpenApi actuatorApi() {
      return GroupedOpenApi.builder().group("actuator").pathsToMatch("/actuator/**").build();
  }

  
}
