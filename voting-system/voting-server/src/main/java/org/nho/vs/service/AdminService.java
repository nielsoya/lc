package org.nho.vs.service;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import org.nho.vs.domain.User;
import org.nho.vs.domain.Dish;
import org.nho.vs.domain.Menu;
import org.nho.vs.domain.UserType;
import org.nho.vs.domain.Restaurant;


import org.nho.vs.dao.UserDao;
import org.nho.vs.dao.MenuDao;
import org.nho.vs.dao.RestaurantDao;
import org.nho.vs.json.MenuItem;
import org.nho.vs.json.MenuForRestaurantRequest;
import org.nho.vs.exception.RestarauntNotFoundException;
import org.nho.vs.exception.RestarauntAlreadyExistsException;


@Service
public class AdminService extends AbstractService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    @Resource
    private UserDao userDao;       

    @Resource
    private RestaurantDao restaurantDao;
    
    @Resource
    private MenuDao menuDao;

    /**
     * Adds restaurant with given name 
     * @param usrName user name 
     * @param rstName restaurant name 
     * @return new created restaurant  
     */
    public Restaurant addRestaurant(final String usrName, final String rstName) {
        checkArguments(usrName, rstName);
        final Restaurant rst = restaurantDao.findByName(rstName);
        if( null != rst ){
            throw new RestarauntAlreadyExistsException("Restaurant with name: "+rstName+" already exists.");
        }
        final User user = userDao.findOne(usrName);
        checkUser(user, usrName, UserType.ADMIN);        
        final Restaurant saved = restaurantDao.save(new Restaurant(rstName, null));
        LOGGER.debug("New restaurant saved: "+saved);
        return saved;
        
    }

    /**
     * Adds menu to the restaurant 
     * @param req Request with new menu for restaurant 
     */
    public void addMenu(final MenuForRestaurantRequest req) {
        checkRequest(req);
        final Restaurant rst = restaurantDao.findOne(req.getRstId());
        if( null == rst ){
            throw new RestarauntNotFoundException("Restaurant with id: "+req.getRstId()+" NOT found.");
        }
        final User user = userDao.findOne(req.getUser());
        checkUser(user, req.getUser(), UserType.ADMIN);
        checkIfMenuIsNotAlreadyAdded(rst, req.date());
        final List<Dish> dishes = req.getDishes().stream().map(MenuItem :: toDish).collect(Collectors.toList());
        final Menu menu = new Menu(req.date(), dishes);
        menu.setRestaraunt(rst);
        menuDao.save(menu);
        LOGGER.debug("New menu saved for restaurant[id="+rst.getId()+"]: "+menu);
    }

    //checking if menu for given restaurant and date was already added 
    private void checkIfMenuIsNotAlreadyAdded(final Restaurant rst, final LocalDate date) {
        if( menuDao.findByRestarauntAndDate(rst.getId(), date) != null ){
        	throw new IllegalStateException("Menu already added to Restaurant[id="+rst.getId()
        		+"] for date: "+date);
        }
    }

    //helper-method for checking menu-addition request 
    private void checkRequest(final MenuForRestaurantRequest req){
        if( null == req ){
            final String errMsg = "MenuForRestaurantRequest is NULL";            
            throw new IllegalArgumentException(errMsg);
        }
        if( req.isEmpty() ){
            final String errMsg = "MenuForRestaurantRequest is Empty: NO dishes in it.";            
            throw new IllegalArgumentException(errMsg);
        }        
    }

    //helper-method : checking incoming parameters  
    private void checkArguments(final String usrName, final String rstName){
        if( null == rstName || rstName.trim().isEmpty()  ){
            final String errMsg = "Restaurant name is missed in request";           
            throw new IllegalArgumentException(errMsg);
        }
        if( null == usrName || usrName.trim().isEmpty() ){
            final String errMsg = "User name is missed in request";           
            throw new IllegalArgumentException(errMsg);
        }
    }
    
}