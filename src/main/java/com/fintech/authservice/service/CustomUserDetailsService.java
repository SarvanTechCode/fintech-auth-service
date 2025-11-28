package com.fintech.authservice.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fintech.authservice.entity.UserEntity;
import com.fintech.authservice.repository.UserRepository;



@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	
private final UserRepository userRepository;
public CustomUserDetailsService(UserRepository userRepository) {
	this.userRepository = userRepository;
}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		
		UserEntity user = userRepository.findByEmail(username).orElseThrow(()-> new RuntimeException("user not found"));
		 username = user.getEmail();	
		String password = user.getPassword();
		Set<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(r->new SimpleGrantedAuthority(r.name())).collect(Collectors.toSet());
		boolean enabled = user.isEnabled();
		
		return new org.springframework.security.core.userdetails.User(
		        user.getEmail(),
		        user.getPassword(),
		        enabled,        // enabled
		        true,           // accountNonExpired
		        true,           // credentialsNonExpired
		        true,           // accountNonLocked
		        authorities);	}
	
	

}
