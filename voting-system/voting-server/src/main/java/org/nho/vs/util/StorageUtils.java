package org.nho.vs.util;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Storage utilities
 * @author persik__pool
 */
@Component
@Transactional
public class StorageUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageUtils.class);

    @PersistenceContext(type = PersistenceContextType.TRANSACTION, unitName = "unit")
    protected EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void shutdown() {
        try {
            if (entityManager != null) {
                entityManager.createNativeQuery("SHUTDOWN").executeUpdate();
            }
        } catch (final Exception e) {
            LOGGER.error("Failed to shutdown sessionFactory: ", e);
        }
    }   

}