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
import javax.security.sasl.AuthenticationException;
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
import javax.xml.bind.DatatypeConverter;
import myapplication.entity.User;
import myapplication.exceptions.CreateException;
import myapplication.exceptions.DeleteException;
import myapplication.exceptions.ReadException;
import myapplication.exceptions.UpdateException;
import myapplication.utils.security.AsymmetricEncryption;
import myapplication.utils.security.Hashing;

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
            AsymmetricEncryption ae = new AsymmetricEncryption();
            Hashing hashing = new Hashing();
            entity.setPassword(hashing.hashString(AsymmetricEncryption.decryptString(entity.getPassword())));
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
     *
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
    @Path("getPublicKey")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getPublicKey() {
        // Devuelve la clave pública
        return DatatypeConverter.printHexBinary(AsymmetricEncryption.getPublicKey().getEncoded());
    }

    /**
     * Gets users by company name
     *
     * @param companyName
     * @return a list of users.
     */
    @GET
    @Path("getUsersByCompanyName/{companyName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findUsersByCompanyName(@PathParam("companyName") String companyName) {
        return super.findUsersByCompanyName(companyName);
    }

    /**
     * Gets users by name.
     *
     * @param companyName
     * @return a list of Users.
     */
    @GET
    @Path("getUsersByName/{name}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findUsersByName(@PathParam("name") String companyName) {
        return super.findUsersByName(companyName);
    }

    /**
     * Gets all the users in the database.
     *
     * @param companyName
     * @return a list of Users.
     */
    @GET
    @Path("getAllUsers")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> getAllUsers() {
        return super.getAllUsers();
    }

    /**
     * POST method to send a new password to the user.
     *
     * @param email The user email.
     */
    @POST
    @Override
    @Path("sendNewPassword/{email}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void sendNewPassword(@PathParam("email") String email) {
        super.sendNewPassword(email);
    }

    /**
     * POST method to recover the user password.
     *
     * @param email The user email.
     */
    @POST
    @Override
    @Path("recoverUserPassword/{email}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void recoverUserPassword(@PathParam("email") String email) {
        super.recoverUserPassword(email);
    }

    /**
     *
     * @param user
     */
    @POST
    @Path("loginUser")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User loginUser(User user) throws AuthenticationException {
        Hashing hashing = new Hashing();
        List<User> listUser = super.findUsersByName(user.getUsername());
        boolean correctPassword = hashing.compareHash(listUser.get(0).getPassword(), AsymmetricEncryption.decryptString(user.getPassword()));
        if (correctPassword) {
            User correctUser = listUser.get(0);
            // FIXME: Al dejar la password vacio, se cambia en la base de datos tambien (no se puede devolver a null)
            //correctUser.setPassword("");
            return correctUser;
        } else {
            throw new AuthenticationException();
        }

    }

    /**
     * Gets the entity manager for this class.
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
