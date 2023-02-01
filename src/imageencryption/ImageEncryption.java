/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package imageencryption;

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
            
            //Use RGB to convert method
            int w = image.getWidth();
            int h = image.getHeight();
            int[][] result = new int[h][w];

            for (int row = 0; row < h; row++) {
                for (int col = 0; col < w; col++) {
                    result[row][col] = image.getRGB(col, row);
                }
            }

            //System.out.println(Arrays.toString(result));


  
            
            
//            // 1. Store rgb values into array
//            int w = image.getWidth(); // width
//            int h = image.getHeight(); // height
//            int total_pixels = (h*w);
//            Color[] colors = new Color[total_pixels];
//            int i = 0;
//            // Collect RGB pixel data with nested for-loop
//            for (int x = 0; x < w; x++) {
//                for (int y = 0; y < h; y++) {
//                    colors[i] = new Color(image.getRGB(x, y));
//                    i++;
//                } // end inner for-loop
//            } // end outer for-loop

            byte[] buffer = new byte[result.length * result[0].length * Integer.BYTES];

            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[0].length; j++) {
                    int index = (i * result[0].length + j) * Integer.BYTES;
                    byte[] bytes = ByteBuffer.allocate(Integer.BYTES).putInt(result[i][j]).array();
                    System.arraycopy(bytes, 0, buffer, index, Integer.BYTES);
                }
            }
            //System.out.println(Arrays.toString(buffer));
//           
//            // 2. Convert int array into byte array for encryption
//            ByteBuffer byteBuffer = ByteBuffer.allocate(result.length * 4);        
//            IntBuffer intBuffer = byteBuffer.asIntBuffer();
//            intBuffer.put(result); //This is the wrong variable but using it to show what's happening
//            byte[] toBeEnc = byteBuffer.array();
            
            try {
                // 3. Set up AES cipher/key and begin encryption
                cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
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
            byte[] encBytes = cipher.doFinal(buffer);
           
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("Encrypted bytes");
            //System.out.println(Arrays.toString(encBytes));
            
            
            
//            int[][] result2 = new int[h][w];
//
//            for (int row = 0; row < h; row++) {
//                for (int col = 0; col < w; col++) {
//                    int index = (row * w + col) * 3;
//                    int r = encBytes[index] & 0xFF;
//                    int g = encBytes[index + 1] & 0xFF;
//                    int b = encBytes[index + 2] & 0xFF;
//                    result2[row][col] = (r << 16) | (g << 8) | b;
//                }
//            }

            // 4. Convert byte array back to int array
            IntBuffer intBuf = ByteBuffer.wrap(encBytes).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
            int[] encArray = new int[intBuf.remaining()];
            intBuf.get(encArray);

            // 5. Convert int array into file format
            DataBuffer rgbData = new DataBufferInt(encArray, encArray.length);
            WritableRaster raster = Raster.createPackedRaster(rgbData, w, h, w, new int[]{0xff0000, 0xff00, 0xff},null);
            ColorModel colorModel = new DirectColorModel(24, 0xff0000, 0xff00, 0xff);
            BufferedImage img = new BufferedImage(colorModel, raster, false, null);
            String fileName = "C:\\Users\\Mark Case\\Pictures\\Saved Pictures\\tux-enc.png";
            ImageIO.write(img, "png", new File(fileName));
            
//            String path = "C:\\Users\\Mark Case\\Pictures\\Saved Pictures\\tux-enc.png";
//            BufferedImage finalImage = new BufferedImage(result2.length, result2[0].length, BufferedImage.TYPE_INT_RGB);
//            for (int x = 0; x < 200; x++) {
//                for (int y = 0; y < 200; y++) {
//                    finalImage.setRGB(x, y, result2[x][y]);
//                }
//            }
//
//            File ImageFile = new File(path);
//            try {
//                ImageIO.write(finalImage, "png", ImageFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        } catch (IOException ex) {
            Logger.getLogger(ImageEncryption.class.getName()).log(Level.SEVERE, null, ex);
        } // end try-catch
        
    
    } // end encryption method
    

} // end ImageEncryption class
