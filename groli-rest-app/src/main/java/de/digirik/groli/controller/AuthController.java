package de.digirik.groli.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.digirik.groli.model.dto.request.LoginRequest;
import de.digirik.groli.model.dto.response.JwtResponse;
import de.digirik.groli.service.auth.AuthService;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/sign-in")
	private JwtResponse signIn(@RequestBody LoginRequest loginRequest) {
		return authService.authenticateUser(loginRequest);
	}
}
