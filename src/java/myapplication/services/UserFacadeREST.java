/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.xml.bind.DatatypeConverter;
import myapplication.entity.User;
import myapplication.exceptions.CreateException;
import myapplication.exceptions.DeleteException;
import myapplication.exceptions.ReadException;
import myapplication.exceptions.UpdateException;
import myapplication.utils.security.AsymmetricEncryption;

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
        try {
            super.create(entity);
        } catch (CreateException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The method to update a user.
     *
     * @param entity
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(User entity) {
        try {
            super.edit(entity);
        } catch (UpdateException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Deletes a user by id
     *
     * @param id
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        try {
            super.remove(super.find(id));
        } catch (ReadException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeleteException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            return super.find(id);
        } catch (ReadException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Path("user/getPublicKey")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getPublicKey() {
       return DatatypeConverter.printHexBinary(AsymmetricEncryption.getPublicKey().getEncoded());
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
