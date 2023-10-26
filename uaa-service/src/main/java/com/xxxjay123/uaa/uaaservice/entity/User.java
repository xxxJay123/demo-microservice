package com.xxxjay123.uaa.uaaservice.entity;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column
	private String password;
	
  @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL,
      fetch = FetchType.EAGER)
	private Set<Roles> roles = new HashSet<>();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.map(
						role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
				.collect(Collectors.toList());
	}

	public void addRole(Roles.RoleType roleType) {
		Roles roles = new Roles(); // Create a Roles object
		roles.setName(roleType); // Set the RoleType enum to the Roles object
		this.roles.add(roles); // Add the Roles object to the user's roles
		roles.getUsers().add(this); // Ensure bidirectional relationship is maintained
	}

	public void setAuthorities(Set<Roles> authorities) {
		this.roles = authorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
