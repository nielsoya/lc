package org.nho.vs.dao;


import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.nho.vs.domain.Restaurant;
import org.nho.vs.repository.RestaurantRepository;


/** JPA-based Restaurant DAO implementation  */
@Service
@Transactional
@Repository("restaurantDao")
public class RestaurantDaoImpl extends BaseDao<Restaurant, Long, RestaurantRepository> implements RestaurantDao{

    @Override
    public Restaurant findByName(final String rstName) {        
        return getRepo().findByName(rstName);
    }
    
    @Override
	protected Class<RestaurantRepository> getRepositoryClass() {
		return RestaurantRepository.class;
	}
	
}