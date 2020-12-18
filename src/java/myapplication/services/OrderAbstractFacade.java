/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import myapplication.entity.Order;
import myapplication.entity.OrderProduct;
import myapplication.entity.Product;
import myapplication.exceptions.ReadException;

/**
 *
 * @author 2dam
 */
public abstract class OrderAbstractFacade extends AbstractFacade<Order> {

    public OrderAbstractFacade(Class<Order> entityClass) {
        super(entityClass);
    }

    public Set<Order> findAllOrders() throws ReadException{
        try {
            return new HashSet<Order>(getEntityManager().createNamedQuery("findAllOrders").getResultList());
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    public Set<Order> findOrdersByPrice(Double price) throws ReadException {
        try {
            return new HashSet<Order>(getEntityManager().createNamedQuery("findOrdersByPrice")
                    .setParameter("price", price).getResultList());
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

}
