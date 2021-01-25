/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication.utils.security;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Mikel
 */
public class AsymmetricEncryption {

    private static ResourceBundle rb = ResourceBundle.getBundle("myapplication.config.config");

    /**
     * Generates RSA Keys. This method shoudn't be called as the keys are
     * already generated.
     */
    public static void generateRSAKeys() {
        try {
            KeyGenerator.generateRSAKkeyPair();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AsymmetricEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Encrypts a String using the public key.
     *
     * @param text
     * @return The text encrypted.
     */
    public static String encryptString(String text) {
        try {
            byte[] cipherText
                    = KeyGenerator.do_RSAEncryption(
                            text,
                            getPublicKey());
            //String stringBytes = Base64.getEncoder().encodeToString(cipherText);
            String stringBytes = toHexString(cipherText);
            return stringBytes;
        } catch (Exception ex) {
            Logger.getLogger(AsymmetricEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static byte[] getPublicFileKey() throws IOException {

        InputStream keyfis = AsymmetricEncryption.class.getClassLoader()
                .getResourceAsStream("myapplication/utils/security/public-key.key");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        // read bytes from the input stream and store them in buffer
        while ((len = keyfis.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
        }
        keyfis.close();
        return os.toByteArray();
    }

    /**
     * Decrypts a string using the private key.
     *
     * @param encryptedString
     * @return
     */
    public static String decryptString(String encryptedString) {
        // Key key = new SecretKeySpec(keyString.getBytes(),0,keyString.getBytes().length, "DES");     
        //byte[] encriptedStringinBytes = encryptedString.getBytes();
        byte[] encriptedStringinBytes = toByteArray(encryptedString);
        try {
            String decryptedText
                    = KeyGenerator.do_RSADecryption(
                            encriptedStringinBytes,
                            getPrivateKey());
            return decryptedText;
        } catch (Exception ex) {
            Logger.getLogger(AsymmetricEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     * Gets the public key used to encrypt.
     *
     * @return the PublicKey.
     */
    public static PublicKey getPublicKey() {
        try {
            String filename = "public-key.key";

            byte[] keyBytes = DatatypeConverter.parseHexBinary(getFileContentAsString(filename));
            //  byte[] keyBytes = getPublicFileKey();
            X509EncodedKeySpec spec
                    = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(AsymmetricEncryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AsymmetricEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets the private key used to encrypt and decrypt.
     *
     * @return the PrivateKey.
     */
    public static PrivateKey getPrivateKey() {
        try {
            String filename = "private-key.key";
            byte[] keyBytes = DatatypeConverter.parseHexBinary(getFileContentAsString(filename));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(AsymmetricEncryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AsymmetricEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets the content of a file as string.
     *
     * @param filename The file to read.
     * @return The content of the file.
     */
    public static String getFileContentAsString(String filename) {
        try {
            //        try {
//
//            //System.out.println("Dirección: " + new File("").getAbsolutePath());
//            Scanner in = new Scanner(new FileReader(new File("").getAbsolutePath() + "\\" + rb.getString("PUBLIC_KEY_NAME")));
//            StringBuilder sb = new StringBuilder();
//            while (in.hasNext()) {
//                sb.append(in.next());
//            }
//            in.close();
//            return sb.toString();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(AsymmetricEncryption.class.getName()).log(Level.SEVERE, null, ex);
//        }
//       
//        return null;
            String e = new String(Files.readAllBytes(Paths.get(AsymmetricEncryption.class.getResource(filename + ".key").toURI())));
            return e;
        } catch (URISyntaxException ex) {
            Logger.getLogger(AsymmetricEncryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AsymmetricEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Converts a ByteArray into a HexString String
     *
     * @param array
     * @return the string in HexString format
     */
    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    /**
     * Converts a String into a ByteArray.
     *
     * @param s The String to convert into ByteArray
     * @return a ByteArray.
     */
    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}
