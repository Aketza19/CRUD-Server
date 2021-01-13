/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.utils.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import myapplication.utils.filesystem.FileHandler;

/**
 *
 * @author Mikel
 */

public class KeyGenerator {

    private static final String RSA = "RSA";

    /**
     * Generates RSA keys using RSA algorythm.
     *
     * @return a Keypair.
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair generateRSAKkeyPair() throws NoSuchAlgorithmException {
        SecureRandom secureRandom
                = new SecureRandom();
        KeyPairGenerator keyPairGenerator
                = KeyPairGenerator.getInstance(RSA);

        keyPairGenerator.initialize(
                2048, secureRandom);

        KeyPair keypair = keyPairGenerator
                .generateKeyPair();
        FileHandler.createFile("private-key.key");
        FileHandler.createFile("public-key.key");
        FileHandler.writeStringToFile("private-key.key", DatatypeConverter.printHexBinary(
                keypair.getPrivate().getEncoded()));
        FileHandler.writeStringToFile("public-key.key", DatatypeConverter.printHexBinary(
                keypair.getPublic().getEncoded()));
        return keypair;
    }

    /**
     * Executes the encryption using the public key.
     *
     * @param plainText The plain text to encrypt.
     * @param publicKey The public key used to encrypt.
     * @return an array of bytes.
     * @throws Exception
     */
    public static byte[] do_RSAEncryption(
            String plainText,
            PublicKey publicKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainText.getBytes());
    }

    /**
     * Decrypts the RSA String using the private key.
     *
     * @param cipherText The text to decrypt.
     * @param privateKey The private key.
     * @return The String decrypted.
     * @throws Exception
     */
    public static String do_RSADecryption(
            byte[] cipherText,
            PrivateKey privateKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(cipherText);

        return new String(result);
    }

}
