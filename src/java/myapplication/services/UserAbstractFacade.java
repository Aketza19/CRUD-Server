/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import java.util.List;
import myapplication.entity.User;

/**
 *
 * @author 2dam
 */
public abstract class UserAbstractFacade extends AbstractFacade<User> {

    public UserAbstractFacade(Class<User> entityClass) {
        super(entityClass);
    }

    public List<User> findUsersByCompanyName(String companyName) {
        return getEntityManager().createNamedQuery("findUsersByCompanyName")
                .setParameter("companyName", companyName).getResultList();
    }
    
     public List<User> findUsersByName(String name) {
        return getEntityManager().createNamedQuery("findUsersByName")
                .setParameter("name", name).getResultList();
    }
}
