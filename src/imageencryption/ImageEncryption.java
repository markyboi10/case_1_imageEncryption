/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package imageencryption;

import static imageencryption.Main.image;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
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
    

    
    public static byte[] encryptionECB() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException  {
        
        // Objects
        File inputFile = new File  ("C:\\Users\\Mark Case\\Pictures\\Saved Pictures\\tux.png"); // File to encrypt
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
            // 3. Set up AES cipher/key and begin encryption
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
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
        cipher.init(Cipher.ENCRYPT_MODE, key); // Encrypt cipher and key
        byte[] encBytes = cipher.doFinal(buffer);// Send enc to new byte array
        
        return encBytes;

        
    
    } // end encryption method
    
        public static byte[] encryptionCBC() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException  {
        
        // Objects
        File inputFile = new File  ("C:\\Users\\Mark Case\\Pictures\\Saved Pictures\\tux.png"); // File to encrypt
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
            // 3. Set up AES cipher/key and begin encryption
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
        cipher.init(Cipher.ENCRYPT_MODE, key); // Encrypt cipher and key
        byte[] encBytes = cipher.doFinal(buffer);// Send enc to new byte array
        
        return encBytes;

        
    
    } // end encryption method
    

} // end ImageEncryption class
