package org.nho.vs.context;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 * Spring MVC configuration/context  
 */
public class SpringMvcContext extends WebMvcConfigurationSupport {
    
    @Resource
    private MappingJackson2HttpMessageConverter  jsonMessageConverter;

    protected void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
        converters.add(jsonMessageConverter);
        addDefaultHttpMessageConverters(converters);
    }

}
