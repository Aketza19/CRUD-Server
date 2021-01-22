/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.services;

import java.util.List;
import java.util.ResourceBundle;
import myapplication.entity.User;
import myapplication.utils.email.EmailService;
import myapplication.utils.security.AsymmetricEncryption;
import myapplication.utils.security.Hashing;

/**
 *
 * @author 2dam
 */
public abstract class UserAbstractFacade extends AbstractFacade<User> {

    private static final ResourceBundle rb = ResourceBundle.getBundle("config.config");

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
        // Get the transmitter encrypted email from the config file
        String transmitterEmail = AsymmetricEncryption.decryptString(rb.getString("TRANSMITTER_EMAIL"));
        // Get the transmitter encrypted password from the config file
        String transmitterPassword = AsymmetricEncryption.decryptString(rb.getString("TRANSMITTER_PASS"));
        // TODO: Si no se encuentra el correo en la base de datos, lanzar una excepcion
        // Call the sendNewPassword method with the transmitter credentials and user email and get the new password
        String newPassword = EmailService.sendNewPassword(transmitterEmail, transmitterPassword, email);
        // Get all users and compare the sended email to the user's email
        List<User> allUsers = getAllUsers();
        for (User user : allUsers) {
            // If the email are equals, update the user with the new password hashed in the database
            if (user.getEmail().equals(email)) {
                // Convert the password in a hash String
                Hashing hashNewPass = new Hashing();
                String newPass = hashNewPass.hashString(newPassword);
                // Update the current user with the new password hashed
                user.setPassword(newPass);
                UserFacadeREST userRest = new UserFacadeREST();
                userRest.edit(user);
                break;
            }
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

    public List<User> getAllUsers() {
        return getEntityManager().createNamedQuery("getAllUsers").getResultList();
    }

}
