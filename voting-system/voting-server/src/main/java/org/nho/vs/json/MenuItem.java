package org.nho.vs.json;

import java.math.BigDecimal;

import org.nho.vs.domain.Dish;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/** Item from MenuForRestaurantrequest - actually a dish */
@JsonRootName(MenuItem.ROOT_NAME)
public final class MenuItem {

    static final String ROOT_NAME = "item";
    
    private static final String NAME  = "name";
    private static final String PRICE = "price";
      
    @JsonProperty(NAME)
    private final String name;
       
    @JsonProperty(PRICE)
    private final BigDecimal price;

    @JsonCreator
    public MenuItem(@JsonProperty(NAME)  final String name, 
                    @JsonProperty(PRICE) final BigDecimal price) {  
        if( null == name || name.trim().isEmpty() ){
        	throw new IllegalArgumentException("Name of the dish is empty");
        }
        if( null == price ){
        	throw new IllegalArgumentException("Price is NULL");
        }
        if( price.compareTo(BigDecimal.ZERO) != 1 ){
        	throw new IllegalArgumentException("Price should be >= 0");
        }
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
    
    @JsonIgnore
    public Dish toDish(){
        return new Dish(name, price);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(33);
        builder.append("MenuItem [name=");
        builder.append(name);
        builder.append(", price=");
        builder.append(price);
        builder.append(']');
        return builder.toString();
    }                        
    
}