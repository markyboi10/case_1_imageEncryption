/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package imageencryption;


import UI.myGUI;
import java.awt.Dimension;
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
import javax.swing.JFrame;

/**
 *
 * @author Mark Case
 */
public class Main {

    public static File inputFile = null;
    public static BufferedImage image = null; // Reading in my input file using BufferedImage Class
     // Frame object
    private static myGUI myGUI;
    
    public static void main(String[] args) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        
        inputFile = new File("C:\\Users\\Mark Case\\Pictures\\Saved Pictures\\tux.png"); // File to encrypt
        image = ImageIO.read(inputFile);  
        
                
                /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(myGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(myGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(myGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(myGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                myGUI = new myGUI();
                //myGUI.setPreferredSize(new Dimension(1000, 700));
                myGUI.pack();
                myGUI.setLocationRelativeTo(null);
                
                myGUI.setVisible(true);
                myGUI.setTitle("Image Encryption Bot");
                myGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        
//        //RGBToBytes.convertImgData2DToByte(image);
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Press enter for ECB or type anything for CBC");
//        String input = scanner.nextLine();
//        if(input.isEmpty()) {
//            BytesToRGB.convertBytesToRGB_ECB();
//        } else {
//            BytesToRGB.convertBytesToRGB_CBC();
//        }

    
        
    }
}
