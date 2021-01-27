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
 * @author Imanol
 */
public abstract class OrderAbstractFacade extends AbstractFacade<Order> {

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
   
    

}
