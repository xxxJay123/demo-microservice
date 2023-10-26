package com.xxxjay123.uaa.uaaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xxxjay123.uaa.uaaservice.entity.Roles;
import com.xxxjay123.uaa.uaaservice.entity.User;
import com.xxxjay123.uaa.uaaservice.model.AuthenticationRequest;
import com.xxxjay123.uaa.uaaservice.model.AuthenticationResponse;
import com.xxxjay123.uaa.uaaservice.model.RegisterRequest;
import com.xxxjay123.uaa.uaaservice.repository.UserRepository;
import com.xxxjay123.uaa.uaaservice.service.CustomUserDetailsService;
import com.xxxjay123.uaa.uaaservice.utils.JwtTokenUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  @Autowired
  private final AuthenticationManager authenticationManager;
  @Autowired
  private final CustomUserDetailsService userDetailsService;
  @Autowired
  private final JwtTokenUtil jwtTokenUtil;
  @Autowired
  private final UserRepository userRepository;


  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    try {
      if (userRepository.existsByUsername(request.getUsername())) {
        return ResponseEntity.badRequest().body("Username is already taken!");
      }

      User result = userDetailsService.registerUser(request);

      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Registration failed!");
    }
  }


  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(),
              request.getPassword()));
    } catch (BadCredentialsException e) {
      throw new RuntimeException("Incorrect username or password", e);
    }

    final UserDetails userDetails =
        userDetailsService.loadUserByUsername(request.getUsername());

    // Assuming generateToken method takes an Authentication object as a parameter
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());

    final String jwt = jwtTokenUtil.generateToken(authenticationToken);

    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }

}


