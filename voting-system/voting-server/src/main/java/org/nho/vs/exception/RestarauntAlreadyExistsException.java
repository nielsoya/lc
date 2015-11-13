package org.nho.vs.exception;

public final class RestarauntAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public RestarauntAlreadyExistsException(final String message){
        super(message);
    }

}

