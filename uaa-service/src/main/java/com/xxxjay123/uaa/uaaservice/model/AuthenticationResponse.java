package com.xxxjay123.uaa.uaaservice.model;

import lombok.Data;

@Data
public class AuthenticationResponse {
  private String jwtToken;

  public AuthenticationResponse(String jwtToken) {
    this.jwtToken = jwtToken;
  }
}
