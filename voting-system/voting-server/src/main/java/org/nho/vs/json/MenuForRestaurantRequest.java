package org.nho.vs.json;

import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;


/** Request to add menu for restaurant */
@JsonRootName(MenuForRestaurantRequest.NAME)
public final class MenuForRestaurantRequest {
    
    static final String NAME = "menu";
    
    private static final String RST_ID  = "rstId";
    private static final String DATE    = "date";
    private static final String USER    = "user";
    private static final String DISHES  = "dishes";
    
    private static final String D_F = "MM-dd-yyyy";

    /** Id of restaurant for which menu need to be added */
    @JsonProperty(RST_ID)
    private final long rstId;
    
    /** Date of menu in DD.MM.YYYY format - could be empty --> current date is used in this case */
    @JsonProperty(DATE)
    private final String date;
    
    @JsonProperty(USER)
    private final String user;
    
    @JsonProperty(DISHES)
    private List<MenuItem> dishes = new ArrayList<>();

    @JsonCreator
    public MenuForRestaurantRequest(@JsonProperty(RST_ID) final long rstId, 
                                    @JsonProperty(DATE)   final String date,
                                    @JsonProperty(USER)   final String user,
                                    @JsonProperty(DISHES) final List<MenuItem> dishes) {        
        if( null == user || user.trim().isEmpty() ){
            throw new IllegalArgumentException("User name is NULL");
        }
    	this.rstId = rstId;
        this.date = date;
        this.user = user;
        if( null != dishes ){
            this.dishes = dishes;
        }
    }

    public long getRstId() {
        return rstId;
    }

    public String getDate() {
        return date;
    }        
    
    public String getUser() {
        return user;
    }

    public List<MenuItem> getDishes() {
        return dishes;
    }
    
    @JsonIgnore
    public boolean isEmpty(){
        return dishes.isEmpty();
    }
    
    @JsonIgnore
    public LocalDate date(){
        if( null != date && !date.trim().isEmpty() ){
            try{                
                return LocalDate.parse(date, DateTimeFormatter.ofPattern(D_F));
            }catch(final Exception ignore){
                return LocalDate.now();
            }            
        }
        return LocalDate.now();
    }   

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(177);
        builder.append("MenuForRestaurantRequest [rstId=");
        builder.append(rstId);
        builder.append(", date=");
        builder.append(date());
        builder.append(", addedBy=");
        builder.append(user);
        builder.append(", dishes=");
        builder.append(dishes);
        builder.append(']');
        return builder.toString();
    }                               

}