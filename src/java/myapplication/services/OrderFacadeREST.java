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
import myapplication.entity.OrderProductId;

/**
 *
 * @author Imanol
 */
@Stateless
@Path("order")
public class OrderFacadeREST extends OrderAbstractFacade {

    private Logger LOGGER = Logger.getLogger(OrderFacadeREST.class.getName());

    @PersistenceContext(unitName = "CRUD-ServerPU")
    private EntityManager em;
    private Boolean exist= false;

    public OrderFacadeREST() {
        super(Order.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Order entity) {
        try {
            for(OrderProduct orderProducts : entity.getProducts()){
                orderProducts.setOrder(entity);
            }
            super.create(entity);
            
            for(OrderProduct entityProduct : entity.getProducts()){
                OrderProduct oProduct  = new OrderProduct();
                oProduct.setId(new OrderProductId(entity.getId(),entityProduct.getProduct().getId()));
                Product product = getEntityManager().find(Product.class, entityProduct.getProduct().getId());
                oProduct.setProduct(product);
                oProduct.setOrder(entity);
                oProduct.setTotal_price(entityProduct.getTotal_price());
                oProduct.setTotal_quantity(entityProduct.getTotal_quantity());
                getEntityManager().persist(oProduct);
            }       
        } catch (CreateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(Order entity) {
        try {
            Order order = super.find(entity.getId());
            for(OrderProduct entityProduct : entity.getProducts()){
                for(OrderProduct findedProduct : order.getProducts()){
                    if(entityProduct.getId().equals(findedProduct.getId())){
                        findedProduct.setTotal_quantity(entityProduct.getTotal_quantity());
                        findedProduct.setTotal_price(entityProduct.getTotal_price());
                        entityProduct.setOrder(findedProduct.getOrder());
                    }
                }
            }
            super.edit(entity);            
        } catch (UpdateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        } catch (ReadException ex) {
            LOGGER.severe(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
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
