/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package imageencryption;

import UI.myGUI;
import static UI.myGUI.path1;
import static UI.myGUI.selFile1;
import static imageencryption.Main.image;
import static imageencryption.Main.image;
import java.awt.Image;
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
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Mark Case
 */
public class BytesToRGB {
    //myGUI myGUI = new myGUI();
    BufferedImage getImg;
    
    public static String fileName = null;
    
    public void convertBytesToRGB_ECB() throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        ImageEncryption imgE = new ImageEncryption();
        byte[] encBytes = imgE.encryptionECB();
        
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
        fileName = "C:\\Users\\Mark Case\\Pictures\\Saved Pictures\\image-enc.png";
        ImageIO.write(img, "png", new File(fileName));
        
        
        
    }
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
        fileName = "C:\\Users\\Mark Case\\Pictures\\Saved Pictures\\image-enc.png";
        ImageIO.write(img, "png", new File(fileName));
        
       

    }




        

    
}
