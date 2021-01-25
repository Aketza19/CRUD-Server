package myapplication.utils.email;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import static org.passay.AllowedCharacterRule.ERROR_CODE;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

/**
 * Builds an Email Service capable of sending normal email to a given SMTP Host.
 * Currently <b>send()</b> can only works with text.
 */
public class EmailService {

    private static final ResourceBundle rb = ResourceBundle.getBundle("config.config");
    // Server mail user & pass account
    private String user = null;
    private String pass = null;

    // DNS Host + SMTP Port
    private String smtp_host = null;
    private int smtp_port = 0;

    @SuppressWarnings("unused")
    private EmailService() {
    }

    /**
     * Builds the EmailService.
     *
     * @param user User account login
     * @param pass User account password
     * @param host The Server DNS
     * @param port The Port
     */
    public EmailService(String user, String pass, String host, int port) {
        this.user = user;
        this.pass = pass;
        this.smtp_host = host;
        this.smtp_port = port;
    }

    /**
     * Sends the given <b>text</b> from the <b>sender</b> to the
     * <b>receiver</b>. In any case, both the <b>sender</b> and <b>receiver</b>
     * must exist and be valid mail addresses. The sender, mail's FROM part, is
     * taken from this.user by default<br/>
     * <br/>
     *
     * Note the <b>user</b> and <b>pass</b> for the authentication is provided
     * in the class constructor. Ideally, the <b>sender</b> and the <b>user</b>
     * coincide.
     *
     * @param receiver The mail's TO part
     * @param subject The mail's SUBJECT
     * @param text The proper MESSAGE
     * @throws MessagingException Is something awry happens
     *
     */
    public void sendMail(String receiver, String subject, String text) throws MessagingException {

        // Mail properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtp_host);
        properties.put("mail.smtp.port", smtp_port);
        properties.put("mail.smtp.ssl.enable", "false");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.trust", smtp_host);
        properties.put("mail.imap.partialfetch", false);

        // Authenticator knows how to obtain authentication for a network connection.
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        // MIME message to be sent
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(user));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver)); // Ej: receptor@gmail.com
        message.setSubject(subject); // Asunto del mensaje

        // A mail can have several parts
        Multipart multipart = new MimeMultipart();

        // A message part (the message, but can be also a File, etc...)
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(text, "text/html");
        multipart.addBodyPart(mimeBodyPart);

        // Adding up the parts to the MIME message
        message.setContent(multipart);

        // And here it goes...
        Transport.send(message);
    }

    /**
     * Method used to generate a random password. This method uses the
     * passay-1.6.0.jar library, it can be downloaded from:
     * https://www.passay.org/downloads/1.6.0/passay-1.6.0-dist.zip. If the link
     * doesn't work, there is the official website: https://www.passay.org/.
     *
     * @return The password generated.
     */
    public static String generatePassayPassword() {
        // Create the PasswordGenerator object.
        PasswordGenerator gen = new PasswordGenerator();

        // Set the rule to the lowercase in the password.
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        // Set the rule to the uppercase in the password.
        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        // Set the rule to the digits in the password.
        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        // Set the rule to the special characters in the password.
        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        // Generate the password with the rules. For length to be evaluated it 
        // must be greater than the number of characters defined in the character rule.
        String password = gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
        return password;
    }

    /**
     * Method used to send a new password to the user.This method uses the
     * generatePassayPassword() method to generate a secure and random password
     * to the user.
     *
     * @param transmitterEmail The transmitter email.
     * @param transmitterPassword The transmitter password.
     * @param receiverEmail The receiver email.
     * @return The generated password.
     */
    public static String sendNewPassword(String transmitterEmail, String transmitterPassword, String receiverEmail) {
        String newPassword = generatePassayPassword();
        EmailService emailService = new EmailService(transmitterEmail,
                transmitterPassword, rb.getString("EMAIL_HOST"), Integer.parseInt(rb.getString("EMAIL_PORT")));
        try {
            emailService.sendMail(receiverEmail, "New password",
                    newPassword);
            System.out.println("Ok, mail sent!");
            return newPassword;
        } catch (MessagingException e) {
            System.out.println("Doh! " + e.getMessage());
        }
        return null;
    }

    /**
     * Method used to recover the user password. This method takes the user
     * password from the database.
     *
     * @param transmitterEmail The transmitter email.
     * @param transmitterPassword The transmitter password.
     * @param receiverEmail The receiver email.
     * @param userPassword The user's password, taken from the database.
     */
    public static void recoverUserPassword(String transmitterEmail, String transmitterPassword, String receiverEmail, String userPassword) {
        EmailService emailService = new EmailService(transmitterEmail,
                transmitterPassword, rb.getString("EMAIL_HOST"), Integer.parseInt(rb.getString("EMAIL_PORT")));
        try {
            emailService.sendMail(receiverEmail, "Recover password",
                    userPassword);
            System.out.println("Ok, mail sent!");
        } catch (MessagingException e) {
            System.out.println("Doh! " + e.getMessage());
        }
    }
}
