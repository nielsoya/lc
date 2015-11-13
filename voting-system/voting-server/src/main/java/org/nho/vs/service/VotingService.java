package org.nho.vs.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import org.nho.vs.dao.UserDao;
import org.nho.vs.dao.VotingDao;
import org.nho.vs.dao.RestaurantDao;
import org.nho.vs.domain.Restaurant;
import org.nho.vs.domain.User;
import org.nho.vs.domain.UserType;
import org.nho.vs.domain.Voting;
import org.nho.vs.exception.RestarauntNotFoundException;
import org.nho.vs.exception.VotingTimeExpiredException;


/**
 * Voting Service - contains logic for voting for restaurants 
 */
@Service
public class VotingService extends AbstractService {

	private static final Logger LOGGER = LoggerFactory.getLogger(VotingService.class);

	@Resource
	private UserDao userDao;
	
	@Resource
	private VotingDao votingDao;

	@Resource
	private RestaurantDao restaurantDao;

	/**
	 * Performs voting for a given restaurant on behalf of given user 
	 * @param rstId
	 * @param usrName
	 */
	public void vote(final Long rstId, final String usrName) {
		checkArguments(rstId, usrName);
		final Restaurant rst = restaurantDao.findOne(rstId);
		if(null == rst){
			throw new RestarauntNotFoundException("Restaurant with Id: "+rstId+" NOT Found.");
		}
		final User user = userDao.findOne(usrName);
		checkUser(user, usrName, UserType.REGULAR);
		checkVotingTime();
		Voting vote = votingDao.findByUserAndDate(usrName, LocalDate.now());
		if(null != vote){
			vote.setRestaraunt(rst);
		} else {
			vote = new Voting(LocalDate.now(), rst, user);
		}
		votingDao.save(vote);
		LOGGER.debug("Vote saved: "+vote);
	}
	
	//helper-method : checking incoming parameters  
	private void checkArguments(final Long rstId, final String usrName){
		if( null == rstId ){
			final String errMsg = "Restaurant Id is missed in request";
			LOGGER.error(errMsg);
			throw new IllegalArgumentException(errMsg);
		}
		if( null == usrName || usrName.trim().isEmpty() ){
			final String errMsg = "User name is missed in request";
			LOGGER.error(errMsg);
			throw new IllegalArgumentException(errMsg);
		}
	}

	//helper-method for checking voting time : should be before 11.00 
	private void checkVotingTime() {
		final LocalDateTime now = LocalDateTime.now();
		if( now.getHour() >= 11 ){
			throw new VotingTimeExpiredException("Voting is allowed only before 11.00, but now it is: "+now);
		}
	}

}