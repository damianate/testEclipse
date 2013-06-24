 
import com.googlecode.javacv.cpp.opencv_core.IplImage;  
import static com.googlecode.javacv.cpp.opencv_core.*; 
import static com.googlecode.javacv.cpp.opencv_imgproc.*;    
import static com.googlecode.javacv.cpp.opencv_highgui.*; 
public class filtrKoloru {
	  
	final static String outPath = "/Users/Szu/Desktop/";
	static IplImage testImage = cvLoadImage("/Users/Szu/Desktop"+"/test.png");
	
	
	public static void main(String[] args) { 
		IplImage hsvImg = cvCreateImage(cvGetSize(testImage), 8, 3); 
		IplImage thresholded = cvCreateImage(cvGetSize(testImage), IPL_DEPTH_8U, 1); 
		cvCvtColor(testImage, hsvImg, CV_BGR2HSV);
		
		CvScalar  hsv_min = cvScalar(120, 0, 130, 0);
		CvScalar  hsv_max = cvScalar(160, 255, 255, 0);/*H-180,S-255,V-255*/
		cvInRangeS(hsvImg, hsv_min, hsv_max, thresholded);
//		  cvSmooth(thresholded, thresholded, CV_MEDIAN, 13);
		
//		cvInRangeS(hsvImg, cvScalar(0, 58, 89,0),cvScalar(25, 173, 229,0),testImage);
		cvSaveImage(outPath + "6-jon_doe_"+".png", thresholded);
		cvSaveImage(outPath + "out2"+".png", hsvImg);
		System.out.print("done");
	}
}
