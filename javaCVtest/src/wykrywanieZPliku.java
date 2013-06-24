/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.googlecode.javacv.CanvasFrame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;

/**
 *
 * @author Szu
 */
public class wykrywanieZPliku {
    private static BufferedImage image2;
     static IplImage image;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         File imageFile = new File("/Users/Szu/Desktop/Praca Magisterska/twarze2/6-jon_doe_37.png");
        try {
            image2 = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
    }
         image=IplImage.createFrom(image2);
          // Read an image.
        
        // Create image window named "My Image".
        //
        // Note that you need to indicate to CanvasFrame not to apply gamma correction, 
        // by setting gamma to 1, otherwise the image will not look correct.
        final CanvasFrame canvas = new CanvasFrame("My Image", 1);
        
        // Request closing of the application when the image window is closed.
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                
        // Show image on window.
        image=toGreyScale();
        canvas.showImage(image);
        
        
    }
     static IplImage toGreyScale(){
     IplImage grayImage = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		cvCvtColor(image, grayImage, CV_BGR2GRAY);
                return grayImage;
       
    }
      public byte[] convert(byte[] imageData) throws IOException {
 
        return imageData;
    }
}
