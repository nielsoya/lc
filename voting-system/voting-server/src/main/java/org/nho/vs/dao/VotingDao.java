package org.nho.vs.dao;

import java.time.LocalDate;

import org.nho.vs.domain.Voting;

public interface VotingDao {
	
	Voting findOne(Long id);
	
	Voting findByUserAndDate(String usrName, LocalDate date);

	Iterable<Voting> findAll();

	Voting save(Voting vtn);

	void delete(Voting vtn);

}
