/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.utils.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mikel
 */
public class Hashing {

    public Hashing() {
    }

    
    /**
     * Generates a hash for a String using SHA-256
     *
     * @param text The text to hash.
     * @return The hash.
     */
    public String hashString(String text) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Compares two hashes.
     *
     * @param hashedStr The hashed string
     * @param plainText The plain text
     * @return Returns true if the hashes match, false if don't
     */
    public boolean compareHash(String hashedStr, String plainText) {
        return hashedStr.equals(hashString(plainText));
    }
}
