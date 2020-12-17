// Java program to perform the
// encryption and decryption
// using asymmetric key
package ejerciciosclavepublica;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

public class EjerciciosClavePublica {

  
    // Driver code
    public static void main(String args[])
            throws Exception {
        KeyPair keypair
                = KeyGenerator.generateRSAKkeyPair();

        String plainText = "Este es el texto plano que se va a encriptar";

        byte[] cipherText
                = KeyGenerator.do_RSAEncryption(
                        plainText,
                        keypair.getPrivate());

        System.out.println(
                "La clave pública es: "
                + DatatypeConverter.printHexBinary(
                        keypair.getPublic().getEncoded()));

        System.out.println(
                "La clave privada es: "
                + DatatypeConverter.printHexBinary(
                        keypair.getPrivate().getEncoded()));

        System.out.print("El texto cifrado es: ");

        System.out.println(
                DatatypeConverter.printHexBinary(
                        cipherText));

        String decryptedText
                = KeyGenerator.do_RSADecryption(
                        cipherText,
                        keypair.getPublic());

        System.out.println(
                "El texto descifrado es: "
                + decryptedText);
    }
}
