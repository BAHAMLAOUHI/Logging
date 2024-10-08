package com.Logger.Logger.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Specify your frontend URL

public class AuthentificationController {
	private  final authenticationService service;

@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
	return ResponseEntity.ok(service.register(request));
}

@PostMapping("/authenticate")
public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody authenticateRequest request) {
	return ResponseEntity.ok(service.authenticate(request));

}
}
