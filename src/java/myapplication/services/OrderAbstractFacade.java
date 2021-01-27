/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.InternalServerErrorException;
import myapplication.entity.Order;
import myapplication.entity.OrderProduct;
import myapplication.entity.Product;
import myapplication.exceptions.CreateException;
import myapplication.exceptions.ReadException;
import myapplication.exceptions.UpdateException;

/**
 *
 * @author 2dam
 */
public abstract class OrderAbstractFacade extends AbstractFacade {

    private Logger LOGGER = Logger.getLogger(OrderFacadeREST.class.getName());
    
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
   
     public void create(OrderProduct entity) throws CreateException {
        try {
            super.edit(entity.getProduct());
            super.create(entity);
        } catch (CreateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        } catch (UpdateException ex) {
            Logger.getLogger(OrderAbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
