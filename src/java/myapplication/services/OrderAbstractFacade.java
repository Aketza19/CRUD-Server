/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import myapplication.entity.Order;

/**
 *
 * @author 2dam
 */
public abstract class OrderAbstractFacade extends AbstractFacade<Order>{
    
    public OrderAbstractFacade(Class<Order> entityClass) {
        super(entityClass);
    }
    
}