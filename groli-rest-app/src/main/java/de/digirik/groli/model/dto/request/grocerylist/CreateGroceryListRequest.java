package de.digirik.groli.model.dto.request.grocerylist;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateGroceryListRequest {

	@JsonProperty(required = true)
	@NotNull(message = "Grocery list name must not be null.")
	private String name;

	@JsonProperty
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
