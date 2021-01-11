package de.digirik.groli.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.digirik.groli.model.dto.request.RegistrationRequest;
import de.digirik.groli.model.entity.user.Role;
import de.digirik.groli.model.entity.user.User;
import de.digirik.groli.model.exception.UserAlreadyExistsException;
import de.digirik.groli.service.user.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('USER')")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	public User createUser(RegistrationRequest registrationRequest)
	        throws UserAlreadyExistsException {
		return userService.createUser(registrationRequest);
	}

	@PostMapping("/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser(String username) {
		userService.deleteUser(username);
	}

	@PostMapping("/add-role")
	@PreAuthorize("hasRole('ADMIN')")
	public User addRoleToUser(String username, Role roleName) {
		return userService.addRole(username, roleName);
	}
}
