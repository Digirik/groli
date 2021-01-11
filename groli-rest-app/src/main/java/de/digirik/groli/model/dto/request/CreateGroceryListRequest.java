package de.digirik.groli.model.dto.request;

import java.util.List;

public class CreateGroceryListRequest {

	private String name;
	private List<String> invitedUsers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getInvitedUsers() {
		return invitedUsers;
	}

	public void setInvitedUsers(List<String> invitedUsers) {
		this.invitedUsers = invitedUsers;
	}
}
