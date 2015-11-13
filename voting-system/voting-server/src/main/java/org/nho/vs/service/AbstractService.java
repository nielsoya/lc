package org.nho.vs.service;

import org.nho.vs.domain.User;
import org.nho.vs.domain.UserType;
import org.nho.vs.exception.UserNotFoundException;
import org.nho.vs.exception.UserPermissionException;

/** Abstract Service - contains common methods */
abstract class AbstractService {

	//helper-method for checking User  
    protected void checkUser(final User user, final String usrName, final UserType type){
        if(null == user){
            throw new UserNotFoundException("User with name: "+usrName+" NOT Found.");
        }
        if(type != user.getType()){
            throw new UserPermissionException("Invalid User's type: "+user.getType()
            		+", while expecting: "+type);
        }
    }
}
