package com.Logger.Logger.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Logger.Logger.config.JwtService;
import com.Logger.Logger.user.Role;
import com.Logger.Logger.user.User;
import com.Logger.Logger.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class authenticationService {

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder()
	               .nom(request.getNom())
	               .prenom(request.getPrenom())
	               .email(request.getEmail())
	               .mdp(passwordEncoder.encode(request.getMdp()))
	               .role(Role.User)
	               .build();

		repository.save(user);
		var jwtToken=jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public AuthenticationResponse authenticate(authenticateRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMdp()));
		var user=repository.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken=jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}
}
