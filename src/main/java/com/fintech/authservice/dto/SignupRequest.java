package com.fintech.authservice.dto;

import java.util.HashSet;
import java.util.Set;

import com.fintech.authservice.entity.Role;

import jakarta.validation.constraints.*;

public class SignupRequest {
	
	  public SignupRequest() {
	        this.roles = new HashSet<>(); // Initialize the Set
	        this.roles.add(Role.ROLE_USER); // Add the default role
	    }
	  
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Size(min = 8)
	private String password;
	
	@NotBlank
	private String fullName;
	
	private Set<Role> roles =  new HashSet<>();

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	
	
	
	

}
