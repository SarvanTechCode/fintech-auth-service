package com.fintech.authservice.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fintech.authservice.dto.AuthResponse;
import com.fintech.authservice.dto.LoginRequest;
import com.fintech.authservice.dto.SignupRequest;
import com.fintech.authservice.entity.UserEntity;
import com.fintech.authservice.repository.UserRepository;

@Service
public class AuthService {
	
	private final  UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,JwtService jwtService) {
		
		this.userRepository = userRepository;
		
		this.passwordEncoder = passwordEncoder;
		
		this.jwtService = jwtService;
	}
	
	
	
	public void registerUser(SignupRequest request) {
		
		
		if(userRepository.existsByEmail(request.getEmail())) {
		    throw new RuntimeException("Email already registered");
		}
		
		UserEntity user= new UserEntity();
		user.setFullName(request.getFullName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setEnabled(true);
		user.setRoles(request.getRoles());
		
		userRepository.save(user);
		
	}
	
	
	public AuthResponse login(LoginRequest request) {
		
		UserEntity userDetails =	userRepository.findByEmail(request.getEmail()).orElseThrow( () ->new RuntimeException("User Not Found"));
		
		if(!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}
		
		 String tokendetails = jwtService.generateToken(userDetails);
		 String refreshToken = UUID.randomUUID().toString();
		 long expiresIn = jwtService.getExpirationMillis();
		 
		 AuthResponse Authresponse1 = new AuthResponse(tokendetails, refreshToken, expiresIn);
		
		return Authresponse1;
		
		
	}
	
	
}
