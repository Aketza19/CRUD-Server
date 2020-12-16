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
import myapplication.entity.User;

/**
 *
 * @author 2dam
 */
@Stateless
@Path("user")
public class UserFacadeREST extends UserAbstractFacade {

    @PersistenceContext(unitName = "CRUD-ServerPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    /**
     * The method to create a new user.
     *
     * @param entity
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(User entity) {
        super.create(entity);
    }

    /**
     * The method to update a user.
     *
     * @param entity
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(User entity) {
        super.edit(entity);
    }

    /**
     * Deletes a user by id
     *
     * @param id
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    /**
     * Gets a user by id.
     * @param id
     * @return A user.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User find(@PathParam("id") Long id) {
        return super.find(id);
    }

    /**
     * Gets users by company name
     * @param companyName
     * @return a list of users.
     */
    @GET
    @Path("user/getUsersByCompanyName/{companyName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findUsersByCompanyName(@PathParam("companyName") String companyName) {
        return super.findUsersByCompanyName(companyName);
    }

    /**
     * Gets users by name.
     * @param companyName
     * @return a list of Users.
     */
    @GET
    @Path("user/getUsersByName/{name}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findUsersByName(@PathParam("name") String companyName) {
        return super.findUsersByName(companyName);
    }

    /**
     * Gets the entity manager for this class.
     * @return 
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
