package de.digirik.groli.service.auth.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import de.digirik.groli.configuration.GroliUserPrincipal;
import de.digirik.groli.model.dto.request.LoginRequest;
import de.digirik.groli.model.dto.response.JwtResponse;
import de.digirik.groli.model.entity.user.User;
import de.digirik.groli.service.auth.AuthService;
import de.digirik.groli.service.util.JwtUtils;

@Service
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;

	private final JwtUtils jwtUtils;

	public AuthServiceImpl(AuthenticationManager authenticationManager,
	        JwtUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	@Override
	public JwtResponse authenticateUser(LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
		    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
		        loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		GroliUserPrincipal userDetails =
		        (GroliUserPrincipal) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities()
		    .stream()
		    .map(GrantedAuthority::getAuthority)
		    .collect(Collectors.toList());

		return new JwtResponse(jwt, userDetails.getUser().getId(),
		    userDetails.getUsername(), roles);
	}

	@Override
	public User getAuthenticatedUser() {
		Authentication authentication =
		        SecurityContextHolder.getContext().getAuthentication();

		GroliUserPrincipal groliUserPrincipal =
		        (GroliUserPrincipal) authentication.getPrincipal();

		return groliUserPrincipal.getUser();
	}
}
