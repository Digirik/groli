package de.digirik.groli.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GroceryListItemDoesNotExistException extends Exception {

	public GroceryListItemDoesNotExistException() {
		super();
	}

	public GroceryListItemDoesNotExistException(String message) {
		super(message);
	}

	public GroceryListItemDoesNotExistException(String message,
	        Throwable cause) {
		super(message, cause);
	}

	public GroceryListItemDoesNotExistException(Throwable cause) {
		super(cause);
	}
}
