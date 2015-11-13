package org.nho.vs.dao;

import java.time.LocalDate;

import org.nho.vs.domain.Menu;

public interface MenuDao {

	Menu findOne(Long id);
	
	Menu findByRestarauntAndDate(long rstId, LocalDate date);

	Iterable<Menu> findAll();

	Menu save(Menu menu);

	void delete(Menu menu);

}