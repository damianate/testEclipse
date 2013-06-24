
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_highgui;
import com.googlecode.javacv.cpp.opencv_objdetect;
import com.googlecode.javacv.cpp.opencv_contrib.FaceRecognizer;
import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.MatVector;


import com.googlecode.javacv.CanvasFrame;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_INTER_AREA;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvResize;
import static com.googlecode.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;

public class ViolaJones {
	
	// ------ Zmienne globalne 
final static opencv_objdetect.CvHaarClassifierCascade FaceCascade = 
	        new opencv_objdetect.CvHaarClassifierCascade(
	            cvLoad("resources/haarcascade_frontalface_default.xml"));

final static opencv_objdetect.CvHaarClassifierCascade EyesCascade = 
			new opencv_objdetect.CvHaarClassifierCascade(
				cvLoad("resources/haarcascade_eye.xml"));

final static String trainingDir = "/Users/Szu/Desktop/Praca Magisterska/twarze2";
final static String outPath = "/Users/Szu/Desktop/";

static FaceRecognizer faceRecognizer;
final static CanvasFrame canvas = new CanvasFrame("My Image", 1);
final static CanvasFrame canvas2 = new CanvasFrame("My Image2", 1);
static ArrayList<IplImage> snapshots = new  ArrayList<IplImage>();
static ArrayList<Integer> predictions = new ArrayList<Integer>();
int Frame=0;
static String InPath = "/Users/Szu/Desktop/Praca Magisterska/twarze";
    
     public static void main(String[] args) throws Exception {

    	 File root = new File(InPath);
		 
	        
	        FilenameFilter pngFilter = new FilenameFilter() {
	            public boolean accept(File dir, String name) {
	                return name.toLowerCase().endsWith(".png");
	            }
	        };

	        File[] imageFiles = root.listFiles(pngFilter);

	        

	        IplImage img;
	        
	   
	        
	        for (File image : imageFiles) {
	            img = cvLoadImage(image.getAbsolutePath());
	            
	           System.out.println(image.getName());
	           
	           opencv_core.IplImage grayImage = opencv_core.IplImage.create(
				        img.width(),
				        img.height(), 
				        opencv_core.IPL_DEPTH_8U, 1);
			 cvCvtColor(img, grayImage, CV_BGR2GRAY);
			 
			 opencv_core.CvMemStorage storage = opencv_core.CvMemStorage.create();
			 
				    
				    
				    opencv_core.CvSeq faces = cvHaarDetectObjects(
					    grayImage, FaceCascade, storage, 1.1, 1, 0);

		    
		    	 if(faces.sizeof() >0)
				 {
			    	for(int i=0;i<faces.sizeof();i++) 
			    	{
			    		
			    	
				      opencv_core.CvRect r = new opencv_core.CvRect(cvGetSeqElem(faces, 0));
				      if(!r.isNull())
				      {
				    	  if(r.width()>20){
			    	  CvRect face = new CvRect(r.x(),r.y(),r.width(),r.height());
			    	  cvSetImageROI(img, face);
			    	  IplImage cropped = cvCreateImage(cvGetSize(img), img.depth(), img.nChannels());
			    	  cvCopy(img, cropped); 
			    	  
			    	  //resize
			    	  IplImage resized = IplImage.create(320, 240, cropped.depth(),cropped.nChannels());
			    	  cvResize(cropped,resized,  CV_INTER_AREA); 
			    	  
			    	  opencv_core.CvSeq eyes = cvHaarDetectObjects(
			    			  resized, EyesCascade, storage, 1.1, 1, 0);
			    	  if(eyes.sizeof() >0)
					     {
			    	  cvSaveImage(outPath + image.getName(), resized);
				    	  }
				      }
				     }
				 }
		     }
				    
	        }
	
			   
			 
				    	   	System.out.println("juz");
				      
				 
		         
			  }
     
 	public Boolean detectEyes(IplImage grayImage)
 	{
 	    	 
 			opencv_core.CvMemStorage storage = opencv_core.CvMemStorage.create();
 			 
 			CvSeq eyes = cvHaarDetectObjects(
 					grayImage,
 					EyesCascade,
 					storage,
 					1.5,
 					3,
 					0);
 			
 			if(eyes.sizeof()>0)
 	    	 return true;
 			else
 			 return false;
 	}
}
