/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import java.util.List;
import myapplication.entity.User;
import myapplication.exceptions.EmailAlreadyExistsException;
import myapplication.exceptions.UsernameAlreadyExistsException;
import myapplication.utils.email.EmailService;

/**
 *
 * @author 2dam
 */
public abstract class UserAbstractFacade extends AbstractFacade<User> {

    public UserAbstractFacade(Class<User> entityClass) {
        super(entityClass);
    }

    /**
     * Finds users by a company name
     *
     * @param companyName
     * @return the list of users.
     */
    public List<User> findUsersByCompanyName(String companyName) {
        return getEntityManager().createNamedQuery("findUsersByCompanyName")
                .setParameter("companyName", companyName).getResultList();
    }

    /**
     * Finds users by user's name
     *
     * @param name
     * @return the list of users.
     */
    public List<User> findUsersByName(String name) {
        return getEntityManager().createNamedQuery("findUsersByName")
                .setParameter("name", name).getResultList();
    }

    /**
     * Method to send a new password to the user by email. This method will take
     * the user email and through the EmailService it will return a new password
     * by means of an mail message.
     *
     * @param email The user email.
     */
    public void sendNewPassword(String email) {
        // Call the sendNewPassword method with the transmitter credentials and user email.
        // TODO: Coger las credenciales del emisor desde los ficheros encriptados.
        EmailService.sendNewPassword("almazon.passw.restore@gmail.com", "AitanaWar88", email);
    }

    /**
     * Method to recover the user password by email. This method will take the
     * user password and through the EmailService it will return their password
     * by means of an mail message.
     *
     * @param email The user email.
     */
    public void recoverUserPassword(String email) {
        // Get the user password by the query.
        String password = (String) getEntityManager().createNamedQuery("getPasswordByEmail")
                .setParameter("email", email).getSingleResult();
        // Call the sendNewPassword method with the transmitter credentials and user's email.
        // TODO: Coger las credenciales del emisor desde los ficheros encriptados.
        EmailService.recoverUserPassword("almazon.passw.restore@gmail.com", "AitanaWar88", email, password);
    }

    /**
     * Gets the list of user stored in the database.
     *
     * @return A list of users.
     */
    public List<User> getAllUsers() {
        return getEntityManager().createNamedQuery("getAllUsers").getResultList();
    }

    /**
     * Finds usernames by email.
     *
     * @param email The email to find.
     * @return An array of users with that email.
     * @throws myapplication.exceptions.EmailAlreadyExistsException
     */
    public List<User> findUserByEmail(String email) throws EmailAlreadyExistsException {
        List<User> userList = getEntityManager().createNamedQuery("findUsersByEmail")
                .setParameter("email", email).getResultList();

        if (!userList.isEmpty()) {
            throw new EmailAlreadyExistsException();
        }
        return userList;
    }

    /**
     * Finds usernames by username.
     *
     * @param username The email to find.
     * @return An array of users with that email.
     * @throws myapplication.exceptions.UsernameAlreadyExistsException
     */
    public List<User> findUserByUsername(String username) throws UsernameAlreadyExistsException {
        List<User> userList = getEntityManager().createNamedQuery("findUsersByUsername")
                .setParameter("username", username).getResultList();

        if (!userList.isEmpty()) {
            throw new UsernameAlreadyExistsException();
        }
        return userList;
    }
}
