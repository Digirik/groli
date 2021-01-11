package de.digirik.groli.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class NotYourGroceryListException extends Exception {

	public NotYourGroceryListException() {
		super();
	}

	public NotYourGroceryListException(String message) {
		super(message);
	}

	public NotYourGroceryListException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotYourGroceryListException(Throwable cause) {
		super(cause);
	}
}
