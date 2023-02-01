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
    

    
    public static byte[] encryption() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException  {
        
        // Objects
        File inputFile = new File  ("C:\\Users\\Mark Case\\Pictures\\Saved Pictures\\tux.png"); // File to encrypt
        Cipher cipher = null; // The cipher object
        KeyGenerator keyGen = null; // The AES key generator
        SecureRandom rand; // A secure random number generator
        byte[] rawIV = new byte[16]; // An AES init. vector
        IvParameterSpec iv; // The IV parameter for CBC
        //            /*
//            Step 1. Read in file and use getRGB to read values into a 2D array
//            */
//            BufferedImage image = ImageIO.read(inputFile); // Reading in my input file using BufferedImage Class
//            
//            int w = image.getWidth(); // Width of image
//            int h = image.getHeight(); // Height of image
//            int[][] result = new int[h][w]; // 2D array decleration of int[height][width]
//            /*
//            Nested for-loop iteration using RGB method to store rgb values in
//            column/row
//            */
//            for (int row = 0; row < h; row++) {
//                for (int col = 0; col < w; col++) {
//                    result[row][col] = image.getRGB(col, row);
//                }
//            }
//
//            //System.out.println(Arrays.toString(result));
//            /*
//            Step 2. Convert the resulting 2D array of rgb values 
//            into a byte array. This way it can encrypted.
//            */
//            byte[] buffer = new byte[result.length * result[0].length * Integer.BYTES]; // Height * Width * 4: Allocating the correct amount of memory
//            /*
//            Nested for-loop iteration to go through the every height and
//            length value and store it in a byte array
//            */
//            for (int i = 0; i < result.length; i++) {
//                for (int j = 0; j < result[0].length; j++) {
//                    int index = (i * result[0].length + j) * Integer.BYTES;
//                    byte[] bytes = ByteBuffer.allocate(Integer.BYTES).putInt(result[i][j]).array();
//                    System.arraycopy(bytes, 0, buffer, index, Integer.BYTES);
//                }
//            }
//            System.out.println(Arrays.toString(buffer));
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
// Encrypt cipher and key
        cipher.init(Cipher.ENCRYPT_MODE, key);
// Send enc to new byte array
        byte[] encBytes = cipher.doFinal(buffer);
//            /*
//            Step 4. Convert byte array back into an array of newly encrypted rgb values
//            */
//            IntBuffer intBuf = ByteBuffer.wrap(encBytes).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
//            int[] encArray = new int[intBuf.remaining()];
//            intBuf.get(encArray);
//
//            /*
//            Step 5. Use raster to convert rgb data, from the array, into a new file 
//            */
//            
//            int width = image.getWidth(); // Width of image
//            int height = image.getHeight(); // Height of image
//            
//            DataBuffer rgbData = new DataBufferInt(encArray, encArray.length);
//            WritableRaster raster = Raster.createPackedRaster(rgbData, width, height, width, new int[]{0xff0000, 0xff00, 0xff},null);
//            ColorModel colorModel = new DirectColorModel(24, 0xff0000, 0xff00, 0xff);
//            BufferedImage img = new BufferedImage(colorModel, raster, false, null);
//            String fileName = "C:\\Users\\Mark Case\\Pictures\\Saved Pictures\\tux-enc.png";
//            ImageIO.write(img, "png", new File(fileName));
        
        return encBytes;

        
    
    } // end encryption method
    

} // end ImageEncryption class
