package imageencryption;

import UI.myGUI;
import static UI.myGUI.selFile1;
import static imageencryption.Main.image;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import javax.swing.JTextField;
import keeptoo.KGradientPanel;

/**
 *
 * @author Mark Case
 */
public class ImageEncryption {

    JTextField testKey = myGUI.getTestKeys(); // Getter from myGUI (@Getter won't work here because I can't edit auto-gen variables on JFrame form)
    
    KGradientPanel rootPanel = myGUI.getRootPanel();
    public byte[] encryptionECB() {

        try {
            image = ImageIO.read(selFile1); // Read it in with BufferedImage class
        } catch (IOException ex) {
            Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        Cipher cipher = null; // The cipher object
        KeyGenerator keyGen = null; // The AES key generator
//        SecureRandom rand; // A secure random number generator
//        byte[] rawIV = new byte[16]; // An AES initialization vector
//        IvParameterSpec iv; // The IV parameter for CBC
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
            // Get a key generator object 
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        keyGen.init(128); // Set the key size to 128 bits

        // Generate the key
        if (testKey.getText().equals("")) { // If jTextField is empty
            SecretKey key = keyGen.generateKey(); // Generate random key
            try {
                cipher.init(Cipher.ENCRYPT_MODE, key); // Encrypt cipher and key
            } catch (InvalidKeyException ex) {
                Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (testKey.getText().length() == 24 && testKey.getText().endsWith("==")) { // If filled, grab it and convert to key
            String key2 = testKey.getText();
            byte[] decodedKey = Base64.getDecoder().decode(key2);
            SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            try {
                cipher.init(Cipher.ENCRYPT_MODE, key); // Encrypt cipher and key
            } catch (InvalidKeyException ex) {
                Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPanel, "Invalid key configuration", "ERROR", JOptionPane.ERROR_MESSAGE); // warning message
            return null;
        }
        System.out.println("DONE");

        byte[] encBytes = null;
        try {
            encBytes = cipher.doFinal(buffer); // Send enc to new byte array
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }

        return encBytes; // Return for BytesToRGB class

    } // End encryptionECB method

    public byte[] encryptionCBC() throws IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = null; // The cipher object
        KeyGenerator keyGen = null; // The AES key generator
        SecureRandom rand; // A secure random number generator
        byte[] rawIV = new byte[16]; // An AES initialization vector
        IvParameterSpec iv = null; // The IV parameter for CBC
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
        System.out.println("Generating key for ECB . . .");
        try {
            // Get a key generator object 
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        keyGen.init(128); // Set the key size to 128 bits
        // Generate the key
        if (testKey.getText().equals("")) {
            SecretKey key = keyGen.generateKey();
            try {
                //Generate the IV for CBC mode
                System.out.println("Generating IV . . .");
                rand = new SecureRandom();
                rand.nextBytes(rawIV); // Fill array with random bytes
                iv = new IvParameterSpec(rawIV);
                try {
                    cipher.init(Cipher.ENCRYPT_MODE, key, iv); // Encrypt cipher and key
                } catch (InvalidAlgorithmParameterException ex) {
                    Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (InvalidKeyException ex) {
                Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (testKey.getText().length() == 24 && testKey.getText().endsWith("==")){
            String key2 = testKey.getText();
            byte[] decodedKey = Base64.getDecoder().decode(key2);
            SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            try {
                //Generate the IV for CBC mode
                System.out.println("Generating IV . . .");
                rand = new SecureRandom();
                rand.nextBytes(rawIV); // Fill array with random bytes
                iv = new IvParameterSpec(rawIV);

                try {
                    cipher.init(Cipher.ENCRYPT_MODE, key, iv); // Encrypt cipher and key
                } catch (InvalidAlgorithmParameterException ex) {
                    Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (InvalidKeyException ex) {
                Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPanel, "Invalid key configuration", "ERROR", JOptionPane.ERROR); // warning message
            return null;
        }
        System.out.println("DONE");

        byte[] encBytes = null;
        try {
            encBytes = cipher.doFinal(buffer); // Send enc to new byte array
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }

        return encBytes; // Return for BytesToRGB class

    } // End encryptionCBC method    

} // End ImageEncryption class
