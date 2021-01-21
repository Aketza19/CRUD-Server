/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import myapplication.entity.Order;
import myapplication.entity.OrderProduct;
import myapplication.entity.Product;
import myapplication.exceptions.CreateException;
import myapplication.exceptions.DeleteException;
import myapplication.exceptions.ReadException;
import myapplication.exceptions.UpdateException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2dam
 */
@Stateless
@Path("order")
public class OrderFacadeREST extends OrderAbstractFacade {

    private Logger LOGGER = Logger.getLogger(OrderFacadeREST.class.getName());

    @PersistenceContext(unitName = "CRUD-ServerPU")
    private EntityManager em;

    public OrderFacadeREST() {
        super(Order.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Order entity) {
        try {
            super.create(entity);
        } catch (CreateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(Order entity) {
        try {
            /*Order order = super.find(entity.getId());
            Set<OrderProduct> product = entity.getProducts();
            for(OrderProduct o1 : order.getProducts()){
            Tengo que recorrer la orden, ver que los id de los productos son iguales a los de la entidad,
            y hacer un set de la id embebida y de la orden.
            }*/
            super.edit(entity);
        } catch (UpdateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        } /*catch (ReadException ex) {
            Logger.getLogger(OrderFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            super.remove(super.find(id));
        } catch (DeleteException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Order find(@PathParam("id") Integer id) {
        try {
            return super.find(id);
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("order")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set<Order> findAllOrders() {
     try {
            Set<Order> orders = super.findAllOrders();
            return orders;
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("order/{price}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set<Order> findOrdersByPrice(@PathParam("price") Double price) {
        try {
            return super.findOrdersByPrice(price);
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
