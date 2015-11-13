package org.nho.vs.dao;

import org.nho.vs.domain.User;

/**
 * User DAO for entity {@link org.nho.vs.domain.User} 
 */
public interface UserDao {
    
    User findOne(String id);

    Iterable<User> findAll();

    User save(User user);

    void delete(User user);

}