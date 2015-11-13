package org.nho.vs.dao;

import org.nho.vs.domain.Restaurant;

public interface RestaurantDao {

	Restaurant findOne(Long id);

	Iterable<Restaurant> findAll();

	Restaurant save(Restaurant rst);

	void delete(Restaurant rst);

    Restaurant findByName(String rstName);

}
