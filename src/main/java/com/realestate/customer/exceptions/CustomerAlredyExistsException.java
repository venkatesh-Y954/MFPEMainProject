package com.realestate.customer.exceptions;

public class CustomerAlredyExistsException extends Exception{

	private static final long serialVersionUID = 1L;

	public CustomerAlredyExistsException(String message) {
		super(message);
	}
}
