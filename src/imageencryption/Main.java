/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package imageencryption;


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.imageio.ImageIO;

/**
 *
 * @author Mark Case
 */
public class Main {

    public static File inputFile = null;
    public static BufferedImage image = null; // Reading in my input file using BufferedImage Class

    public static void main(String[] args) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        
        inputFile = new File("C:\\Users\\Mark Case\\Pictures\\Saved Pictures\\tux.png"); // File to encrypt
        image = ImageIO.read(inputFile);  

        //RGBToBytes.convertImgData2DToByte(image);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter for ECB or type anything for CBC");
        String input = scanner.nextLine();
        if(input.isEmpty()) {
            BytesToRGB.convertBytesToRGB_ECB();
        } else {
            BytesToRGB.convertBytesToRGB_CBC();
        }
    
        
    }
}
