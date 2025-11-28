package com.fintech.authservice.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.authservice.dto.AuthResponse;
import com.fintech.authservice.dto.LoginRequest;
import com.fintech.authservice.dto.SignupRequest;
import com.fintech.authservice.entity.UserEntity;
import com.fintech.authservice.repository.UserRepository;
import com.fintech.authservice.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	
	private final AuthService authService;
	private final  UserRepository userRepository;

	public AuthController(AuthService authService,UserRepository userRepository) {
		this.authService = authService;
		this.userRepository = userRepository;
		
	}
	
@PostMapping("/signup")
public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest request){
	
	System.out.println("check1------");
   authService.registerUser(request);

   log.info("User registered successfully: {}", request.getEmail());
	return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","you Succesfully singed up"));
	
	
	
}

@PostMapping("/login")

public ResponseEntity<?> userlogin(@RequestBody @Valid LoginRequest request ){
	
	AuthResponse arsp = authService.login(request);
	
	System.out.println(arsp.toString());
	
	return ResponseEntity.ok(arsp.toString());
}

@GetMapping("/me")
public List<UserEntity> me(Authentication authentication){
	
	if(authentication == null) {
		
		return (List<UserEntity>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	System.out.println("starting---------");
	
	return  userRepository.findAll();
	
	
}

	

}
