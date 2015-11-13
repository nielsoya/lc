package org.nho.vs.dao;


import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.nho.vs.domain.Voting;
import org.nho.vs.repository.VotingRepository;

/** JPA-based Voting DAO implementation  */
@Service
@Transactional
@Repository("votingDao")
public class VotingDaoImpl extends BaseDao<Voting, Long, VotingRepository> implements VotingDao {

	@Override
	public Voting findByUserAndDate(final String usrName, final LocalDate date) {
		return getRepo().findByUserAndDate(usrName, date);
	}
	
	@Override
	protected Class<VotingRepository> getRepositoryClass() {
		return VotingRepository.class;
	}

}