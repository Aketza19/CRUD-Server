/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import myapplication.entity.User;
import myapplication.exceptions.EmailAlreadyExistsException;
import myapplication.exceptions.UsernameAlreadyExistsException;
import myapplication.utils.email.EmailService;
import myapplication.utils.security.AsymmetricEncryption;
import myapplication.utils.security.Hashing;

/**
 *
 * @author 2dam
 */
public abstract class UserAbstractFacade extends AbstractFacade<User> {

    private static final ResourceBundle rb = ResourceBundle.getBundle("config.config");
    private static final Logger logger = Logger.getLogger("myapplication.services.UserAbstractFacade");

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
                .setParameter("username", name).getResultList();
    }

    /**
     * Method to send a new password to the user by email. This method will take
     * the user email and through the EmailService it will return a new password
     * by means of an mail message.
     *
     * @param email The user email.
     */
    public void sendNewPassword(String email) {
        boolean finded = false;

        // Get all users and compare the sended email to the user's email
        List<User> allUsers = getAllUsers();
        for (User user : allUsers) {
            // If the email are equals, update the user with the new password hashed in the database
            if (user.getEmail().equals(email)) {
                finded = true;
                // Get the transmitter encrypted email from the config file
                String transmitterEmail = AsymmetricEncryption.decryptString(rb.getString("TRANSMITTER_EMAIL"));
                // Get the transmitter encrypted password from the config file
                String transmitterPassword = AsymmetricEncryption.decryptString(rb.getString("TRANSMITTER_PASS"));
                // Call the sendNewPassword method with the transmitter credentials and user email and get the new password
                String newPassword = EmailService.sendNewPassword(transmitterEmail, transmitterPassword, email);

                // Convert the password in a hash String
                Hashing hashNewPass = new Hashing();
                String newPass = hashNewPass.hashString(newPassword);
                // Update the current user with the new password hashed
                user.setPassword(newPass);
                break;
            }
        }
        if (!finded) {
            logger.log(Level.INFO, email + " not found in the database");
        }
    }

    /**
     * Method to recover the user password by email. This method will take the
     * user password and through the EmailService it will return their password
     * by means of an mail message.
     *
     * @param email The user email.
     */
    public void recoverUserPassword(String email) {
        // Get the user password by the query and decrypt it
        String password = (String) getEntityManager().createNamedQuery("getPasswordByEmail")
                .setParameter("email", email).getSingleResult();
        password = AsymmetricEncryption.decryptString(password);
        // Get the transmitter encrypted email from the config file
        String transmitterEmail = AsymmetricEncryption.decryptString(rb.getString("TRANSMITTER_EMAIL"));
        // Get the transmitter encrypted password from the config file
        String transmitterPassword = AsymmetricEncryption.decryptString(rb.getString("TRANSMITTER_PASS"));
        // Call the sendNewPassword method with the transmitter credentials and user's email.
        EmailService.recoverUserPassword(transmitterEmail, transmitterPassword, email, password);
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
