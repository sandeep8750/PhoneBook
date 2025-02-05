package com.sandeep.phonebook.helper;


public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException() {
		super("Resource Not found");
	}
}
