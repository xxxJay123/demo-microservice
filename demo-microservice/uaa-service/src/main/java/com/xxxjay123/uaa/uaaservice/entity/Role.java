package com.xxxjay123.uaa.uaaservice.entity;

import org.springframework.security.core.GrantedAuthority;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Role implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getAuthority() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
