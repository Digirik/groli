package de.digirik.groli.model.dto.request.grocerylist;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InviteToGroceryListRequest extends BaseGroceryListMutationRequest {

	@JsonProperty(required = true)
	@NotEmpty(
	        message = "List of users to invite to grocery list must not be empty.")
	private List<String> usernames;

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}
}
