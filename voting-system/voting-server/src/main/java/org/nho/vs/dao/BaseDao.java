package org.nho.vs.dao;


import java.io.Serializable;

import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.nho.vs.context.ContextHolder;

/**
 * Base/abstract DAO implementation using Spring DAO support
 */
abstract class BaseDao<T, ID extends Serializable, R extends PagingAndSortingRepository<T, ID>> {

    private R repo;

    public T save(T s) {
        return getRepo().save(s);
    }

    public <S extends T> Iterable<S> save(Iterable<S> ses) {
        return getRepo().save(ses);
    }

    public T findOne(ID id) {
        return getRepo().findOne(id);
    }

    public boolean exists(ID id) {
        return getRepo().exists(id);
    }

    public Iterable<T> findAll() {
        return getRepo().findAll();
    }

    public Iterable<T> findAll(Iterable<ID> ids) {
        return getRepo().findAll(ids);
    }

    public long count() {
        return getRepo().count();
    }

    public void delete(ID id) {
        getRepo().delete(id);
    }

    public void delete(T t) {
        getRepo().delete(t);
    }

    public void delete(Iterable<? extends T> ts) {
        getRepo().delete(ts);
    }

    protected abstract Class<R> getRepositoryClass();

    protected R getRepo() {
        if (repo != null) {
            return repo;
        }
        final ApplicationContext applicationContext = ContextHolder.getAppCtx();
        if (applicationContext == null) {
            throw new IllegalStateException("Application context is not initialized yet");
        }
        try {
            repo = applicationContext.getBean(getRepositoryClass());
            return repo;
        } catch (final Exception ignore) {
            throw new IllegalStateException("Database connection is not initialized yet", ignore);
        }
    }
}
