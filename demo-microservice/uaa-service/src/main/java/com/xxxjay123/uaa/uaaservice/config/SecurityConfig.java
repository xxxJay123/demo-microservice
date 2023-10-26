package com.xxxjay123.uaa.uaaservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.xxxjay123.uaa.uaaservice.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {


  private final CustomUserDetailsService customUserDetailsService;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http//
        .csrf(csrf -> csrf//
            .disable()//
        )//
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//
        )//
        .authorizeHttpRequests((requests) -> requests//
            // .requestMatchers(HttpMethod.POST, WHITE_LIST_URL).permitAll() // allow CORS option calls for Swagger UI
            .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()//
            .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()//
            .anyRequest().authenticated()//

        );//

    // .authenticationProvider(//
    // authenticationProvider())//
    // .addFilterBefore(jwtAuthenticationFilter, //
    // UsernamePasswordAuthenticationFilter.class);//


    return http.build();//
  }


  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(customUserDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());

      auth.authenticationProvider(authProvider);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
   CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("*");
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    UrlBasedCorsConfigurationSource source =
        new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
