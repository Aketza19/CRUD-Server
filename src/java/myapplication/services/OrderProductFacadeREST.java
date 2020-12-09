/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import java.util.List;
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
import javax.ws.rs.core.PathSegment;
import myapplication.entity.OrderProduct;
import myapplication.entity.OrderProductId;

/**
 *
 * @author 2dam
 */
@Stateless
@Path("myapplication.entity.orderproduct")
public class OrderProductFacadeREST extends AbstractFacade<OrderProduct> {

    @PersistenceContext(unitName = "CRUD-ServerPU")
    private EntityManager em;

    private OrderProductId getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;orderId=orderIdValue;productId=productIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        myapplication.entity.OrderProductId key = new myapplication.entity.OrderProductId();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> orderId = map.get("orderId");
        if (orderId != null && !orderId.isEmpty()) {
            key.setOrderId(new java.lang.Integer(orderId.get(0)));
        }
        java.util.List<String> productId = map.get("productId");
        if (productId != null && !productId.isEmpty()) {
            key.setProductId(new java.lang.Integer(productId.get(0)));
        }
        return key;
    }

    public OrderProductFacadeREST() {
        super(OrderProduct.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(OrderProduct entity) {
        super.create(entity);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(OrderProduct entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        myapplication.entity.OrderProductId key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public OrderProduct find(@PathParam("id") PathSegment id) {
        myapplication.entity.OrderProductId key = getPrimaryKey(id);
        return super.find(key);
    }

}
