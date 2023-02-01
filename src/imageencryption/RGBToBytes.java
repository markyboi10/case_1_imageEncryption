/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package imageencryption;

import static imageencryption.Main.image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Mark Case
 */
public class RGBToBytes {
    
    public RGBToBytes() {

//        try {
////            File inputFile = new File("C:\\Users\\Mark Case\\Pictures\\Saved Pictures\\tux.png"); // File to encrypt
////            BufferedImage image = ImageIO.read(inputFile); // Reading in my input file using BufferedImage Class
//        } catch (IOException ex) {
//            Logger.getLogger(RGBToBytes.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public static byte[] convertImgData2DToByte(BufferedImage image) {

        /*
            Step 1. Read in file and use getRGB to read values into a 2D array
         */
        int w = image.getWidth(); // Width of image
        int h = image.getHeight(); // Height of image
        int[][] result = new int[h][w]; // 2D array decleration of int[height][width]
        /*
            Nested for-loop iteration using RGB method to store rgb values in
            column/row
         */
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                result[row][col] = image.getRGB(col, row);
            }
        }

        /*
            Step 2. Convert the resulting 2D array of rgb values 
            into a byte array. This way it can encrypted.
         */
        byte[] buffer = new byte[result.length * result[0].length * Integer.BYTES]; // Height * Width * 4: Allocating the correct amount of memory
        /*
            Nested for-loop iteration to go through the every height and
            length value and store it in a byte array
         */
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                int index = (i * result[0].length + j) * Integer.BYTES;
                byte[] bytes = ByteBuffer.allocate(Integer.BYTES).putInt(result[i][j]).array();
                System.arraycopy(bytes, 0, buffer, index, Integer.BYTES);
            }
        }
        //System.out.println(Arrays.toString(buffer));
        return buffer;
    }

}
