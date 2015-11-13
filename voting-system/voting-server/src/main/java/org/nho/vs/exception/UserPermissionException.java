package org.nho.vs.exception;

public final class UserPermissionException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UserPermissionException(final String message){
		super(message);
	}

}
