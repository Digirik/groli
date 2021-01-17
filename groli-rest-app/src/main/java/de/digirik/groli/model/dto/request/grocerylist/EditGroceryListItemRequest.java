package de.digirik.groli.model.dto.request.grocerylist;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditGroceryListItemRequest extends AddGroceryListItemRequest {

	@JsonProperty(required = true)
	@NotNull(message = "Grocery list item id must not be null.")
	private Long groceryListItemId;

	public Long getGroceryListItemId() {
		return groceryListItemId;
	}

	public void setGroceryListItemId(Long groceryListItemId) {
		this.groceryListItemId = groceryListItemId;
	}
}
