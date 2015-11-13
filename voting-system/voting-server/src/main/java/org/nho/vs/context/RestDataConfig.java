package org.nho.vs.context;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import org.springframework.boot.autoconfigure.data.rest.SpringBootRepositoryRestMvcConfiguration;



/**
 * REST data configuration for persistence 
 */
@Configuration
@Import(RepositoryRestMvcConfiguration.class)
public class RestDataConfig extends SpringBootRepositoryRestMvcConfiguration {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RestDataConfig.class);

    @Resource
    private ObjectMapper primaryObjectMapper;

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        super.configureRepositoryRestConfiguration(config);
        try {
            config.setBaseUri(new URI("/persistent-api"));
        } catch (final URISyntaxException e) {
            LOGGER.error("Failed to config repository: ", e );
        }
    }
    
}
