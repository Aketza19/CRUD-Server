// Java program to perform the
// encryption and decryption
// using asymmetric key
package myapplication.utils.security;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

public class Encryption {

    public static void generateRSAKeys() {
        try {
            KeyGenerator.generateRSAKkeyPair();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

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
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static PublicKey getPublicKey() {
        try {
            String filename = "public-key.key";
            //byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
            byte[] keyBytes = DatatypeConverter.parseHexBinary(getFileContentAsString(filename));

            X509EncodedKeySpec spec
                    = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static PrivateKey getPrivateKey() {
        try {
            String filename = "private-key.key";
            byte[] keyBytes = DatatypeConverter.parseHexBinary(getFileContentAsString(filename));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getFileContentAsString(String filename) {
        try {
            Scanner in = new Scanner(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            while (in.hasNext()) {
                sb.append(in.next());
            }
            in.close();
            return sb.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String hashString(String text) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean compareHash(String hashedStr, String plainText) {
        if (!hashedStr.equals(hashString(plainText))) {
            return false;
        } else {
            return true;
        }
    }

    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }

}
