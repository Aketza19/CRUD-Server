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
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotAuthorizedException;
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
import myapplication.exceptions.EmailAlreadyExistsException;
import myapplication.exceptions.EmailAndUsernameAlreadyExistException;
import myapplication.exceptions.ReadException;
import myapplication.exceptions.UpdateException;
import myapplication.exceptions.UsernameAlreadyExistsException;
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
            // At first, searches for the email and the username in the database. If they exist, an exception is thrown.
            List<User> userListEmail = null;
            List<User> userListUsername = null;
            userListUsername = super.findUserByUsername(entity.getUsername());
            userListEmail = super.findUserByEmail(entity.getEmail());
            if (!userListEmail.isEmpty() && !userListUsername.isEmpty()) {
                throw new EmailAndUsernameAlreadyExistException();
            } else if (!userListEmail.isEmpty()) {
                throw new EmailAlreadyExistsException();
            } else if (!userListUsername.isEmpty()) {
                throw new UsernameAlreadyExistsException();
            }

            // Creates a new Asymmetric encryption and hasing object.
            AsymmetricEncryption ae = new AsymmetricEncryption();
            Hashing hashing = new Hashing();
            // Decrypts the password using the private key, hashes it and saves it to the current user object.
            entity.setPassword(hashing.hashString(AsymmetricEncryption.decryptString(entity.getPassword())));
            // Stores the user in the database.
            super.create(entity);
        } catch (CreateException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EmailAlreadyExistsException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            throw new ForbiddenException("Email already exists");
        } catch (UsernameAlreadyExistsException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("Username already exists");
        } catch (EmailAndUsernameAlreadyExistException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAllowedException("Email and username already exist.");
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
     * Returns the public key.
     *
     * @return
     */
    @GET
    @Path("getPublicKey")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getPublicKey() {
        // returns the public key
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
     *
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
    public void sendNewPassword(@PathParam("email") String email) throws UpdateException {
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
     * POST method to do login.
     *
     * @param user
     * @return 
     */
    @POST
    @Path("loginUser")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User loginUser(User user) throws NotAuthorizedException {
        return super.loginUser(user);
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
