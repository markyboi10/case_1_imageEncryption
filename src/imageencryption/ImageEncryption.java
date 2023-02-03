package imageencryption;

import UI.myGUI;
import static imageencryption.Main.image;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.imageio.ImageIO;

/**
 *
 * @author Mark Case
 */
public class ImageEncryption {
    
    
    public byte[] encryptionECB() throws BadPaddingException, IllegalBlockSizeException  {
       
        // Objects
        File f = myGUI.getSelFile1(); // Retrieve selected file from GUI
        try {
            image = ImageIO.read(f); // Read it in with BufferedImage class
        } catch (IOException ex) {
            Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        Cipher cipher = null; // The cipher object
        KeyGenerator keyGen = null; // The AES key generator
        SecureRandom rand; // A secure random number generator
        byte[] rawIV = new byte[16]; // An AES initialization vector
        IvParameterSpec iv; // The IV parameter for CBC
        // Btye array inheriting return value from method
        byte[] buffer = RGBToBytes.convertImgData2DToByte(image);
        
        /*
        Step 3. Encryption of byte array
        */
        try {
            // Set up AES cipher/key and begin encryption
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Generating key for ECB . . .");
        try {
            // Get a key generator object and set the key size to 128 bits
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        keyGen.init(128);
        // Generate the key
        SecretKey key = keyGen.generateKey();
        System.out.println("DONE");  
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key); // Encrypt cipher and key
        } catch (InvalidKeyException ex) {
            Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] encBytes = cipher.doFinal(buffer); // Send enc to new byte array
        
        return encBytes; // Return for BytesToRGB class

    } // End encryptionECB method
    
        public byte[] encryptionCBC() throws  IllegalBlockSizeException, BadPaddingException  {
        
        // Objects
        Cipher cipher = null; // The cipher object
        KeyGenerator keyGen = null; // The AES key generator
        SecureRandom rand; // A secure random number generator
        byte[] rawIV = new byte[16]; // An AES init. vector
        IvParameterSpec iv; // The IV parameter for CBC

        // Btye array inheriting return value from method
        byte[] buffer = RGBToBytes.convertImgData2DToByte(image);
        /*
        Step 3. Encryption of byte array
         */
        try {
            // Set up AES cipher/key and begin encryption
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Generating key . . .");
        try {
            // Get a key generator object and set the key size to 128 bits
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        keyGen.init(128);
        // Generate the key
        SecretKey key = keyGen.generateKey();
        System.out.println("DONE");
        // Generate the IV for CBC mode
        System.out.println("Generating IV . . .");
        rand = new SecureRandom();
        rand.nextBytes(rawIV); // Fill array with random bytes
        iv = new IvParameterSpec(rawIV);
        System.out.println("DONE");
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, iv); // Encrypt cipher and key
        } catch (InvalidAlgorithmParameterException | InvalidKeyException ex) {
            Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] encBytes = cipher.doFinal(buffer);// Send enc to new byte array
        
        return encBytes;// Return for BytesToRGB class

    } // End encryptionCBC method    

} // End ImageEncryption class
