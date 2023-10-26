package com.xxxjay123.uaa.uaaservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerJwtAutoConfiguration;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.xxxjay123.uaa.uaaservice.service.CustomUserDetailsService;
import com.xxxjay123.uaa.uaaservice.utils.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Order(1)
public class SecurityConfig {
  @Autowired
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  @Autowired
  private final PasswordEncoder passwordEncoder;
  @Autowired
  private final JwtAuthEntryPoint jwtAuthEntryPoint;
  @Autowired
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
            .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()//
            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()//
            .anyRequest().authenticated()//
        )//
        .authenticationProvider(//
          authenticationProvider())//
        .addFilterBefore(jwtAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class);//

    return http.build();//
  }

  @Bean
  AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider =
        new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    return daoAuthenticationProvider;
  }

//  /*  @Bean
//   JwtAuthenticationFilter jwtAuthenticationFilter() {
//     return new JwtAuthenticationFilter();
//   }


  @Bean
   AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
