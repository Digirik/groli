package de.digirik.groli.model.dto.request.grocerylist;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseGroceryListMutationRequest {

	@JsonProperty(required = true)
	@NotNull(message = "Grocery list id must not be null.")
	private Long groceryListId;

	public Long getGroceryListId() {
		return groceryListId;
	}

	public void setGroceryListId(Long groceryListId) {
		this.groceryListId = groceryListId;
	}
}
