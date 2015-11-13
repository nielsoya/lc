package org.nho.vs.rest;



import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.nho.vs.domain.Restaurant;
import org.nho.vs.json.MenuForRestaurantRequest;
import org.nho.vs.exception.RestarauntAlreadyExistsException;
import org.nho.vs.exception.RestarauntNotFoundException;
import org.nho.vs.exception.UserNotFoundException;
import org.nho.vs.exception.UserPermissionException;
import org.nho.vs.exception.VotingTimeExpiredException;
import org.nho.vs.service.AdminService;
import org.nho.vs.service.VotingService;


/**
 * REST Controller for restaurants
 * - add new restaurant
 * - add menu to the restaurant 
 * - vote for the restaurant 
 */
@RestController
@RequestMapping(value = "/rest-api/rstrn")
public class RestaurantRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantRestController.class);

    @Resource 
    private AdminService adminService;
    
    @Resource 
    private VotingService votingService;

    /** 
     * Add new restaurant 
     * @param rstName name of restaurant 
     * @param usrName administartor's Id 
     * @return 201 HTTP status in case of success, else --> 400, 401, 409, 500 
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseBody   
    public ResponseEntity addRestaurant(@RequestParam("rstName") final String rstName,
                                        @RequestParam("usrName") final String usrName) {
        LOGGER.debug("#addRestaurant(Restaurant name="+rstName+", User name="+usrName+")");
        try {                
            final Restaurant rst = adminService.addRestaurant(usrName, rstName);
            final String msgOk = "Restaraunt added: "+rst; 
            LOGGER.info(msgOk);
            return new ResponseEntity<>(msgOk, HttpStatus.CREATED);
        } catch (final IllegalArgumentException iae) {
            LOGGER.error("HTTP_CODE_400 --> BAD_REQUEST: ", iae);
            return new ResponseEntity<>(iae.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (final UserNotFoundException | UserPermissionException une) {
            LOGGER.error("HTTP_CODE_401 --> UNATHORIZED: ", une);
            return new ResponseEntity<>(une.getMessage(), HttpStatus.UNAUTHORIZED);        
        } catch(final RestarauntAlreadyExistsException aee){
            LOGGER.error("HTTP_CODE_409 --> CONFLICT: ", aee);
            return new ResponseEntity<>(aee.getMessage(), HttpStatus.CONFLICT);         
        } catch (final Exception e) {
            LOGGER.error("Failed to add restaurant: ", e);
            return new ResponseEntity<>("Failed to add restaurant: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Add new menu for existing restaurant for given day 
     * @param req JSON with menu 
     * @return 201 HTTP status in case of success, else --> 400, 401, 404, 409, 500  
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/menu/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addMenu(@RequestBody final MenuForRestaurantRequest req) {
        LOGGER.debug("#addMenu("+req+")");
        try {                
            adminService.addMenu(req);
            final String msgOk = "Menu added: "+req; 
            LOGGER.info(msgOk);
            return new ResponseEntity<>(msgOk, HttpStatus.CREATED);
        } catch (final IllegalArgumentException iae) {
            LOGGER.error("HTTP_CODE_400 --> BAD_REQUEST: ", iae);
            return new ResponseEntity<>(iae.getMessage(), HttpStatus.BAD_REQUEST);                    
        } catch (final RestarauntNotFoundException rnfe) {
            LOGGER.error("HTTP_CODE_404 - NOT_FOUND - Restaraunt Not found: ", rnfe);
            return new ResponseEntity<>(rnfe.getMessage(), HttpStatus.NOT_FOUND);
        } catch (final UserNotFoundException | UserPermissionException une) {
            LOGGER.error("HTTP_CODE_401 --> UNATHORIZED: ", une);
            return new ResponseEntity<>(une.getMessage(), HttpStatus.UNAUTHORIZED);   
        } catch(final IllegalStateException ise){
            LOGGER.error("HTTP_CODE_409 --> CONFLICT: ", ise);
            return new ResponseEntity<>(ise.getMessage(), HttpStatus.CONFLICT);         
        } catch (final Exception e) {
            LOGGER.error("Failed to add menu: ", e);
            return new ResponseEntity<>("Failed to add menu: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity vote4Restaraunt(@RequestParam("rstId")   final Long rstId,
                                          @RequestParam("usrName") final String usrName) {
        LOGGER.debug("#vote4Restaraunt[[restaurantId={},userName={}]]", rstId, usrName);
        try {
            votingService.vote(rstId, usrName);
            LOGGER.debug("Voted OK");
            return new ResponseEntity<>("Voted OK", HttpStatus.OK);
        } catch (final IllegalArgumentException iae) {
            LOGGER.error("HTTP_CODE_400 --> BAD_REQUEST: ", iae);
            return new ResponseEntity<>(iae.getMessage(), HttpStatus.BAD_REQUEST);        
        }
        catch (final RestarauntNotFoundException rnfe) {
            LOGGER.error("HTTP_CODE_404 - NOT_FOUND - Restaraunt Not found: ", rnfe);
            return new ResponseEntity<>(rnfe.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (final UserNotFoundException | UserPermissionException une) {
            LOGGER.error("HTTP_CODE_401 --> UNATHORIZED: ", une);
            return new ResponseEntity<>(une.getMessage(), HttpStatus.UNAUTHORIZED);        
        } 
        catch (final VotingTimeExpiredException vte) {
            LOGGER.error("HTTP_CODE_403 - FORBIDDEN --> Voting timed out[after 11.00]: ", vte);
            return new ResponseEntity<>(vte.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch(final Exception ex) {
            LOGGER.error("Failed to vote for restaurant: ", ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}