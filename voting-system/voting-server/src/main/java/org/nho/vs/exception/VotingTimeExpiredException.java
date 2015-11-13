package org.nho.vs.exception;

public final class VotingTimeExpiredException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public VotingTimeExpiredException(final String message){
		super(message);
	}

}

