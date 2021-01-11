package de.digirik.groli.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO: remove this and item exception and create 'EntityDoesNotExist' exception?
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class GroceryListDoesNotExistException extends Exception {

	public GroceryListDoesNotExistException() {
	}

	public GroceryListDoesNotExistException(String message) {
		super(message);
	}

	public GroceryListDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public GroceryListDoesNotExistException(Throwable cause) {
		super(cause);
	}

}
