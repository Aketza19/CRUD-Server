/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import myapplication.entity.Order;
import myapplication.entity.OrderProduct;
import myapplication.entity.Product;

/**
 *
 * @author 2dam
 */
@Stateless
@Path("order")
public class OrderFacadeREST extends OrderAbstractFacade {

    @PersistenceContext(unitName = "CRUD-ServerPU")
    private EntityManager em;

    public OrderFacadeREST() {
        super(Order.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Order entity) {
        try{
            super.create(entity);
        }catch(){
            
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(Order entity) {
        try{
            super.edit(entity);
        }catch(){
            
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try{
            super.remove(super.find(id));
        }catch(){
            
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Order find(@PathParam("id") Integer id) {
        try{
            return super.find(id);
        }catch(){
            
        }
    }
    
    @GET
    @Path("order")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set<Order> find() {
        try{
            return super.findAllOrders();
        }catch(){
            
        }
    }
    
    @GET
    @Path("order/{price}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set<Order> findOrdersByPrice(@PathParam("price") Double price) {
        try{
            return super.findOrdersByPrice(price);
        }catch(){
            
        }
    }

    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
