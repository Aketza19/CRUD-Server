/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.utils.security;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Mikel
 */
public class KeyGenerator {

    private static final String RSA
            = "RSA";
    private static Scanner sc;

    // Generating public & private keys
    // using RSA algorithm.
    public static KeyPair generateRSAKkeyPair() throws NoSuchAlgorithmException {
        SecureRandom secureRandom
                = new SecureRandom();
        KeyPairGenerator keyPairGenerator
                = KeyPairGenerator.getInstance(RSA);

        keyPairGenerator.initialize(
                2048, secureRandom);

        KeyPair keypair = keyPairGenerator
                .generateKeyPair();
        createKeyFile("private-key");
        createKeyFile("public-key");
        saveKeyToFile("private-key", DatatypeConverter.printHexBinary(
                keypair.getPrivate().getEncoded()));
        saveKeyToFile("public-key", DatatypeConverter.printHexBinary(
                keypair.getPublic().getEncoded()));
        return keypair;
    }

    // Encryption function which converts
    // the plainText into a cipherText
    // using private Key.
    public static byte[] do_RSAEncryption(
            String plainText,
            PublicKey publicKey)
            throws Exception {
        Cipher cipher
                = Cipher.getInstance(RSA);

        cipher.init(
                Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(
                plainText.getBytes());
    }

    // Decryption function which converts
    // the ciphertext back to the
    // orginal plaintext.
    public static String do_RSADecryption(
            byte[] cipherText,
            PrivateKey privateKey)
            throws Exception {
        Cipher cipher
                = Cipher.getInstance(RSA);

        cipher.init(Cipher.DECRYPT_MODE,
                privateKey);
        byte[] result
                = cipher.doFinal(cipherText);

        return new String(result);
    }

    public static void createKeyFile(String filename) {
        try {
            File myObj = new File(filename + ".key");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public static void saveKeyToFile(String filename, String key) {
        try {
            FileWriter myWriter = new FileWriter(filename + ".key");
            myWriter.write(key);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
}
