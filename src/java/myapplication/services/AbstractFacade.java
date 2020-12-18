/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import myapplication.exceptions.CreateException;
import myapplication.exceptions.DeleteException;
import myapplication.exceptions.ReadException;
import myapplication.exceptions.UpdateException;

/**
 * TODO: add exception and queries
 *
 * @author 2dam
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) throws CreateException{
        try{
            getEntityManager().persist(entity);
        }catch(Exception e){
            throw new CreateException(e.getMessage());
        }
    }

    public void edit(T entity) throws UpdateException {
        try{
            getEntityManager().merge(entity);
        }catch(Exception e){
            throw new UpdateException(e.getMessage());
        }
    }

    public void remove(T entity) throws DeleteException {
        try{
            getEntityManager().remove(getEntityManager().merge(entity));
        }catch(Exception e){
            throw new DeleteException(e.getMessage());
        }
    }

    public T find(Object id) throws ReadException{
        try{
            return getEntityManager().find(entityClass, id);
        }catch(Exception e){
            throw new ReadException(e.getMessage());
        }
    }

}
