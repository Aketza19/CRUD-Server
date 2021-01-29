# Almazon: Server Side

### Requirements to run the server:

1. The Glassfish 4.1 server **MUST** have the json exchange feature enabled and working, **otherwise you will not be able to log into the app**.

2. The Glassfish server must be running on the port 8080.



### Configure server for JSON responses:

1. Download this jar from [org.eclipse.persistence.moxy.jar - Google Drive](https://drive.google.com/file/d/1-ubLLgVRjagJGzApejbumyQz-ZgQld1U/view?usp=sharing)

2. Save and replace this file into ```[YOUR__GLASSFISH__SERVER__DIRECTORY]/glassfish/modules```

3. You're now able to get JSON responses.

### Warnings

1. Some of the requests sent by a browser will return 500 http codes because **the client side of the project uses RSA asymmetric encryption** (user login, user creation).
   
   1. To solve this, you can use this **encrypted user password:** 
      
      ```
      B8F68405858C11B934DD995CF4FC476075C9AD79284F1814E237816C0A1CB7CA507EF05E332DB6F49A58B77F219CCF3F04C07461DF3B74DD818D56EF955026C6CD70D9E1A9A446A6AC24F2C8FA823E0127A775C320BF02169295C7E817191E5012606CA00A950E402168E109344040814AC85680C16C6623F692E7B48702CAE5F043A5CE6A40DE29A7950108ED365960956E3B0D027BA694FFF4E0D39BE9A087E3F9C150F21B4CA731A63C5FB5835B2AF7D65022876294AACFE5C9AC6AC43A545CDAC0342978E9EC5B142965FC047BA1933E7842C1B265EAE76B66DFC9AE781C29B41BFDB80566452BFEB91E32255A9F36449415E3F6B02FDE0810137B219AD1
      ```

2. As we said in the requirements section, we need the json exchange feature enabled in Glassfish, so **the getPublicKey get request will only work if requested as JSON**.

3. 

#### Entities:

**User:** Mikel (Login modification to use encryption, email to recover password: Iker)

**Order:** Imanol

**Aketza:** Product

**Iker:** Company

## Dependencies:

1. Hibernate 4.3.x (JPA 2.1)

2. Passay 1.6.0