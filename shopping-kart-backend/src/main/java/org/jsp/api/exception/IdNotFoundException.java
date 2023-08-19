package org.jsp.api.exception;

public class IdNotFoundException extends RuntimeException{

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Invalid id";
	}
	
}
