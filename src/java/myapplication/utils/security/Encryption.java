// Java program to perform the
// encryption and decryption
// using asymmetric key
package myapplication.utils.security;

import java.security.KeyPair;
import javax.xml.bind.DatatypeConverter;

public class Encryption {

  
    // Driver code
    public void generateEncryptionKeys(){
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
