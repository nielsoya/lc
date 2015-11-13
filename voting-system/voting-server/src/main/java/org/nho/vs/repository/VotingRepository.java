package org.nho.vs.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.nho.vs.domain.Voting;

/** Voting repository with HATEOAS-JSON REST support */
@RepositoryRestResource(collectionResourceRel = "voting", path = "voting")
public interface VotingRepository extends PagingAndSortingRepository<Voting, Long> {
	
	@Query("SELECT vot FROM Voting vot WHERE vot.user.name =:usrName AND vot.date =:date")
	Voting findByUserAndDate(@Param("usrName") String usrName, @Param("date") LocalDate date);
	
}
