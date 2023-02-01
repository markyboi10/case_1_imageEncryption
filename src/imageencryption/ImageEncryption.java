/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package imageencryption;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
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
    
    public static void encryption() throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        
        // Variables
        File inputFile = new File  ("C:\\Users\\Mark Case\\Pictures\\Saved Pictures\\tux.png"); //File to encrypt
        Cipher cipher = null; // The cipher object.
        KeyGenerator keyGen = null; // The AES keygenerator.
        SecureRandom rand; // A secure random number generator.
        byte[] rawIV = new byte[16]; // An AES init. vector.
        IvParameterSpec iv; // The IV parameter for CBC. 
    
        try {
            
            BufferedImage image = ImageIO.read(inputFile); //reading in my input file
            
            // 1. Store rgb values into array
            int w = image.getWidth(); // width
            int h = image.getHeight(); // height
            int total_pixels = (h*w);
            Color[] colors = new Color[total_pixels];
            int i = 0;
            // Collect RGB pixel data with nested for-loop
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    colors[i] = new Color(image.getRGB(x, y));
                    i++;
                } // end inner for-loop
            } // end outer for-loop
            
            // 2. Convert int array into byte array for encryption
            ByteBuffer byteBuffer = ByteBuffer.allocate(colors.length * 4);        
            IntBuffer intBuffer = byteBuffer.asIntBuffer();
            intBuffer.put(total_pixels); //This is the wrong variable but using it to show what's happening
            byte[] toBeEnc = byteBuffer.array();
            
            try {
                // 3. Set up AES cipher/key and begin encryption
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
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
            // Encrypt cipher and key
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // Send enc to new byte array
            byte[] encBytes = cipher.doFinal(toBeEnc);
           

            
        } catch (IOException ex) {
            Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
        } // end try-catch
        
    
    } // end encryption method
    
} // end ImageEncryption class
