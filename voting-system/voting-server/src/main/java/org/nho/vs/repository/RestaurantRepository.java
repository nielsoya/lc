package org.nho.vs.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import org.nho.vs.domain.Restaurant;

/** Restaurant repository with HATEOAS-JSON REST support */
@RepositoryRestResource(collectionResourceRel = "restaurant", path = "restaurant")
public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, Long> {
    
    @Query("SELECT rst FROM Restaurant rst WHERE rst.name =:rstName")
    Restaurant findByName(@Param("rstName") String rstName);
    
}