package de.digirik.groli.service.auth;

import de.digirik.groli.model.dto.request.LoginRequest;
import de.digirik.groli.model.dto.response.JwtResponse;
import de.digirik.groli.model.entity.user.User;

public interface AuthService {

	JwtResponse authenticateUser(LoginRequest loginRequest);

	User getAuthenticatedUser();
}
