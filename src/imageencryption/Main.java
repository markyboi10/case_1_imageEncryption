package imageencryption;

import UI.myGUI;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JFrame;

/**
 *
 * @author Mark Case
 */
public class Main {

    // Objects
    public static File inputFile = null;
    public static BufferedImage image = null; 
    // Frame object
    private static myGUI myGUI;
    
    public static void main(String[] args) {
        
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
            @Override
            public void run() {
                myGUI = new myGUI();
                myGUI.pack();
                myGUI.setLocationRelativeTo(null);
                myGUI.setVisible(true);
                myGUI.setTitle("Image Encryption Program");
                myGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        
    }
}
