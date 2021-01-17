package de.digirik.groli.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO: remove this exception (and the others) and create 'EntityDoesNotExist' exception?
//TODO: check how to return messages to client
//TODO: do a message with String... like you did at work for templating
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
