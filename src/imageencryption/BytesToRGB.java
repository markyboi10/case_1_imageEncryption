package imageencryption;

import static imageencryption.Main.image;
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
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.imageio.ImageIO;

/**
 *
 * @author Mark Case
 */
public class BytesToRGB {

    // Objects
    BufferedImage getImg;
    public static String fileName = null;

    public void convertBytesToRGB_ECB() throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Calling my ImageEncryption class
        ImageEncryption imgE = new ImageEncryption();
        byte[] encBytes = imgE.encryptionECB();

        /*
        Step 4. Convert byte array back into an array of newly encrypted rgb values
         */
        IntBuffer intBuf = ByteBuffer.wrap(encBytes).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer(); // Converts byte array to int array by wrapping the byte array, ordering it, and using asInt to finally convert
        // ^ Litte_ENDIAN = storing least significant byte first, LARGE_ENDIAN does the opposite
        int[] encArray = new int[intBuf.remaining()]; // Creates the int array to equal size of the intBuffer
        intBuf.get(encArray); // Storing elements into int array

        /*
        Step 5. Use raster to convert rgb data, from the array, into a new file 
         */
        int width = image.getWidth(); // Width of image
        int height = image.getHeight(); // Height of image

        DataBuffer rgbData = new DataBufferInt(encArray, encArray.length); // Stores int array data of length encArray
        WritableRaster raster = Raster.createPackedRaster(rgbData, width, height, width, new int[]{0xff0000, 0xff00, 0xff}, null); // Using rgbData, width and height are given positions. The bit represnetaion is for RGB values starting with the least significant first
        ColorModel colorModel = new DirectColorModel(24, 0xff0000, 0xff00, 0xff); // Color model of 24 bits per pixel
        BufferedImage img = new BufferedImage(colorModel, raster, false, null); // img uses colorModel and raster to create the png. False for masking and null for properties
        fileName = System.getProperty("user.home") + File.separator + "Pictures\\image-enc.png"; // New file location
        ImageIO.write(img, "png", new File(fileName)); // Saves as png

    } // End convertBytesToRGB_ECB method

    /*
    Same code but for CBC encrypted byte array
     */
    public void convertBytesToRGB_CBC() throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        ImageEncryption imgE = new ImageEncryption();
        byte[] encBytes = imgE.encryptionCBC();
        /*
            Step 4. Convert byte array back into an array of newly encrypted rgb values
         */
        IntBuffer intBuf = ByteBuffer.wrap(encBytes).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
        int[] encArray = new int[intBuf.remaining()];
        intBuf.get(encArray);

        /*
        Step 5. Use raster to convert rgb data, from the array, into a new file 
         */
        int width = image.getWidth(); // Width of image
        int height = image.getHeight(); // Height of image

        DataBuffer rgbData = new DataBufferInt(encArray, encArray.length);
        WritableRaster raster = Raster.createPackedRaster(rgbData, width, height, width, new int[]{0xff0000, 0xff00, 0xff}, null);
        ColorModel colorModel = new DirectColorModel(24, 0xff0000, 0xff00, 0xff);
        BufferedImage img = new BufferedImage(colorModel, raster, false, null);
        fileName = System.getProperty("user.home") + File.separator + "Pictures\\image-enc.png";
        ImageIO.write(img, "png", new File(fileName));

    } // End convertBytesToRGB_CBC method

} // End BytesToRGB class
