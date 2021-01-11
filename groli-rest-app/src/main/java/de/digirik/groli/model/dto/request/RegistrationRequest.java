package de.digirik.groli.model.dto.request;

import java.util.List;

import de.digirik.groli.model.entity.user.Role;

public class RegistrationRequest {

	private String username;
	private String password;
	private List<Role> roleNames;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(List<Role> roleNames) {
		this.roleNames = roleNames;
	}
}
