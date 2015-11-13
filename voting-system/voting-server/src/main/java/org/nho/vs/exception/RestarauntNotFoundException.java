package org.nho.vs.exception;

public final class RestarauntNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RestarauntNotFoundException(final String message){
		super(message);
	}

}
