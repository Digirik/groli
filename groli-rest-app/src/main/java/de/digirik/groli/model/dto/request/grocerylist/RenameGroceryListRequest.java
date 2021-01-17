package de.digirik.groli.model.dto.request.grocerylist;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RenameGroceryListRequest extends BaseGroceryListMutationRequest {

	@JsonProperty(required = true)
	@NotNull(message = "New grocery list name must not be null.")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
