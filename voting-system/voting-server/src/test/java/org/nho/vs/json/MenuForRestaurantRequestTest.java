package org.nho.vs.json;



import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

public class MenuForRestaurantRequestTest {
    
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        Jackson2ObjectMapperFactoryBean jacksonObjectMapperFactoryBean = new Jackson2ObjectMapperFactoryBean();
        jacksonObjectMapperFactoryBean.setFeaturesToEnable(new Object[]{
                SerializationFeature.WRAP_ROOT_VALUE,
                SerializationFeature.INDENT_OUTPUT,
                SerializationFeature.CLOSE_CLOSEABLE,
                DeserializationFeature.UNWRAP_ROOT_VALUE,
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
        });
        jacksonObjectMapperFactoryBean.afterPropertiesSet();
        objectMapper = jacksonObjectMapperFactoryBean.getObject();
    }   
    
    @Test 
    public void testConstruction() throws IOException{
        final MenuItem salad  = new MenuItem("Crab salad", BigDecimal.valueOf(5.50D));
        final MenuItem soup   = new MenuItem("Tomato soup", BigDecimal.valueOf(7.25D));
        final MenuItem burger = new MenuItem("Chili burger", BigDecimal.valueOf(11.0D));
        final List<MenuItem> dishes = Arrays.asList(salad, soup, burger);
        final MenuForRestaurantRequest menu = new MenuForRestaurantRequest(121L, "11-22-2015", "superman", dishes);
        Assert.assertNotNull(menu.date());
        Assert.assertEquals(22, menu.date().getDayOfMonth());
        //testing to JSON
        String json = objectMapper.writeValueAsString(menu);
        Assert.assertNotNull(json);
        System.out.println(json);   
        //and back to object
        final MenuForRestaurantRequest mReq = objectMapper.readValue(json, MenuForRestaurantRequest.class);
        Assert.assertNotNull(mReq);
        Assert.assertEquals("superman", mReq.getUser());
    }

}