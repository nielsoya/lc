package org.nho.vs.dao;


import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.nho.vs.domain.User;
import org.nho.vs.repository.UserRepository;

/** JPA-based User DAO implementation  */
@Service
@Transactional
@Repository("userDao")
public class UserDaoImpl extends BaseDao<User, String, UserRepository> implements UserDao {

    @Override
    protected Class<UserRepository> getRepositoryClass() {      
        return UserRepository.class;
    }

}