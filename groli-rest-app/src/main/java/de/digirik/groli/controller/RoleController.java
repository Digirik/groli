package de.digirik.groli.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.digirik.groli.model.entity.user.Role;

@RequestMapping("/api/role")
@RestController
public class RoleController {

	@GetMapping("/all")
	private List<Role> getAllUserRoles() {
		return Arrays.asList(Role.values().clone());
	}
}
