package com.xxxjay123.uaa.uaaservice.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.xxxjay123.uaa.uaaservice.entity.Roles;
import com.xxxjay123.uaa.uaaservice.entity.User;
import com.xxxjay123.uaa.uaaservice.entity.Impl.CustomUserDetails;
import com.xxxjay123.uaa.uaaservice.model.RegisterRequest;
import com.xxxjay123.uaa.uaaservice.repository.UserRepository;
import com.xxxjay123.uaa.uaaservice.utils.JwtTokenUtil;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(
          "User not found with username: " + username);
    }
    Set<GrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority(Roles.RoleType.USER.name()));
    Authentication authentication = new UsernamePasswordAuthenticationToken(
        user, authorities, user.getAuthorities());

    String token = jwtTokenUtil.generateToken(authentication);
    return new CustomUserDetails(user, token);
  }

  public User registerUser(RegisterRequest request) {
    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    // Assign roles based on the registration source
    if (isGitHubRegistration(request)) {
      user.addRole(Roles.RoleType.EDITOR);
    } else {
      user.addRole(Roles.RoleType.USER);
    }

    // Save the user to the database
    return userRepository.save(user);
  }

  private boolean isGitHubRegistration(RegisterRequest request) {
    return false;
  }

}
