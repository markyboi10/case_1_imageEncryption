package imageencryption;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author Mark Case
 */
public class RGBToBytes {

    public static byte[] convertImgData2DToByte(BufferedImage image) {
        /*
        Step 1. Read in file and use getRGB to read values into a 2D array.
        This is a neat, much faster alternative (https://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image)
        I found over using BufferedImage's getRGB method. This approach 
        returns the rgb values directly for each pixel while getRGB combines the rgb values into
        one int. No more nested for-loop!
         */
        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData(); //accesss pixels directly
        final int width = image.getWidth(); //width
        final int height = image.getHeight(); //height
        final boolean hasAlphaChannel = image.getAlphaRaster() != null; //boolean for if an alpha exists

        int[][] result = new int[height][width]; // initialization of 2D array
        if (hasAlphaChannel) { //check boolean
            final int pixelLength = 4; //argb = 4
            //Contructing int representation by shifting the values to the correct position(24, 0, 8, 16)
            for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) { // When column index reaches end, increment to next row
                    col = 0;
                    row++;
                } // end if
            } // End first for
        } else { // if no alpha channel, work with just rgb values
            final int pixelLength = 3; // ^
            // Same as before 
            for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // Blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // Green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // Red
                result[row][col] = argb;
                col++;
                if (col == width) { // Same as before
                    col = 0;
                    row++;
                } // End if
            } // End second for
        } // end else
        
        /*
        Step 2. Convert the resulting 2D array of rgb values 
        into a byte array. This way it can encrypted.
        */
        int sizeOfResult = 0; // Initialization 
        for (int i = 0; i < result.length; i++) { // Counts each row length to find total number of ints
            sizeOfResult += result[i].length;
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(sizeOfResult * 4); // Memory allocation (each int requires 4 bytes)
        IntBuffer intBuffer = byteBuffer.asIntBuffer(); // Holds same memory allocation as byteBuffer

        for (int i = 0; i < result.length; i++) { // Loops through again to store every int into the intBuffer
            intBuffer.put(result[i]);
        }

        byte[] buffer = byteBuffer.array(); // Final byte representation 

        return buffer; // Return value for ImageEncryption class
        
    } // End convertImgData2DToByte method

} // End RGBToBytes Class
