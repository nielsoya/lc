package org.nho.vs.context;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.ConfigurableApplicationContext;



/**
 * Spring Context holder 
 *
 * @author persik__pool
 */
public class ContextHolder {
    
    private static final String MAPPER_NAME = "mapper";   
    
    private static ConfigurableApplicationContext appCtx;

    public static ConfigurableApplicationContext getAppCtx() {
        return appCtx;
    }

    public static void setAppCtx(ConfigurableApplicationContext appCtx) {
        ContextHolder.appCtx = appCtx;
    }

    public static ObjectMapper getObjectMapper() {
        return (ObjectMapper)appCtx.getBean(MAPPER_NAME);
    }   

}