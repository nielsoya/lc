package org.nho.vs.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import org.nho.vs.domain.Menu;

/** Menu repository with HATEOAS-JSON REST support */
@RepositoryRestResource(collectionResourceRel = "menu", path = "menu")
public interface MenuRepository extends PagingAndSortingRepository<Menu, Long> {
	
	@Query("SELECT menu FROM Menu menu WHERE menu.restaraunt.id =:rstId AND menu.date =:date")
	Menu findByRestarauntAndDate(@Param("rstId") long rstId, @Param("date") LocalDate date);

}
