package com.xxxjay123.uaa.uaaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.xxxjay123.uaa.uaaservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);


  Boolean existsByUsername(String username);
}
