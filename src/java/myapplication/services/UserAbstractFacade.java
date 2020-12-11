/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import javax.persistence.EntityManager;
import myapplication.entity.User;

/**
 *
 * @author 2dam
 */
public abstract class UserAbstractFacade extends AbstractFacade<User> {

    public UserAbstractFacade(Class<User> entityClass) {
        super(entityClass);
    }

}
