package com.xxxjay123.uaa.uaaservice.model;

import lombok.Data;

@Data
public class AuthenticationRequest {
  private String username;
  private String password;
}
