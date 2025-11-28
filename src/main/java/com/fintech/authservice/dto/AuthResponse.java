package com.fintech.authservice.dto;

import java.util.Set;

import com.fintech.authservice.entity.Role;

public class AuthResponse {
	
	private String token;
	
	private String refreshToken;

	private long expiresIn;

	public AuthResponse(String token, String refreshToken, long expiresIn) {
		super();
		this.token = token;
		this.refreshToken = refreshToken;
		this.expiresIn = expiresIn;
	}

	@Override
	public String toString() {
		return "AuthResponse [token=" + token + ", refreshToken=" + refreshToken + ", expiresIn=" + expiresIn + "]";
	}


	
	

}
