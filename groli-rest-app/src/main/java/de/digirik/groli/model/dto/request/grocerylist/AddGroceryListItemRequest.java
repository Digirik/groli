package de.digirik.groli.model.dto.request.grocerylist;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddGroceryListItemRequest extends BaseGroceryListMutationRequest {

	@JsonProperty(required = true)
	@NotNull(message = "Grocery list item description must not be null.")
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
