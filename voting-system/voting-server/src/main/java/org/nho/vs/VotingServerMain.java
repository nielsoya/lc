package org.nho.vs;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//Spring 
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.metadata.DataSourcePoolMetadataProvidersConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.jta.JtaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.nho.vs.context.ContextHolder;

import org.nho.vs.util.StorageUtils;


/**
 * Main class for Voting Server 
 *
 * @author persik__pool  
 */
@ImportResource("classpath*:vts-context.xml")
@Configuration
@Import({
        HypermediaAutoConfiguration.class,
        AopAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        DataSourcePoolMetadataProvidersConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        DispatcherServletAutoConfiguration.class,
        EmbeddedServletContainerAutoConfiguration.class,
        ErrorMvcAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        HttpEncodingAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        JacksonAutoConfiguration.class,
        JmxAutoConfiguration.class,
        JtaAutoConfiguration.class,
        MultipartAutoConfiguration.class,
        PersistenceExceptionTranslationAutoConfiguration.class,
        PropertyPlaceholderAutoConfiguration.class,
        ServerPropertiesAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        WebSocketAutoConfiguration.class
})
@EnableJpaRepositories(basePackages = {"org.nho.vs"})
public class VotingServerMain {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(VotingServerMain.class);

    private static ConfigurableApplicationContext appCtx;

    private static StorageUtils storageUtils;
    
    public static void main(final String[] args) {
        System.out.println("Trying to start Voting Server ...");
        start();        
    }            
        
    /** Starts BSE */
    public static void start() {
        try {
            LOGGER.info("Start Voting Server ...");
            appCtx = new SpringApplicationBuilder().showBanner(true).sources(VotingServerMain.class).run();
            ContextHolder.setAppCtx(appCtx);
            storageUtils = ((StorageUtils) appCtx.getBean("storageUtils"));
            final String startedMsg = "\n ####################### VOTING_SERVER_STARTED ############################### \n";
            System.out.println(startedMsg);
            LOGGER.info(startedMsg);
        } catch (final Exception e) {
            LOGGER.error("Failed to start VTS: ", e);
            shutdownDataSource();
            LOGGER.warn("Shut-down[System.exit(1)], Bye!");
            System.exit(1);
        }
    }

    @PreDestroy
    public void shutdown() {       
        shutdownDataSource();
    }

    private static void shutdownDataSource(){
       LOGGER.info("Shut down embedded database.");
       if(null != storageUtils){
    	   storageUtils.shutdown();
       }
    }

}