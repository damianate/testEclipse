import java.io.File;
import java.io.FilenameFilter;

import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_contrib.*;

public class javaCV_PCA {
 
    static String trainingDir = "/Users/Szu/Desktop/twarze";
    static IplImage testImage = cvLoadImage("/Users/Szu/Desktop"+"/test.png");
    
     
	public static void main(String[] args) {
		File root = new File(trainingDir);
		 
	        
	        FilenameFilter pngFilter = new FilenameFilter() {
	            public boolean accept(File dir, String name) {
	                return name.toLowerCase().endsWith(".png");
	            }
	        };

	        File[] imageFiles = root.listFiles(pngFilter);

	        MatVector images = new MatVector(imageFiles.length);

	        int[] labels = new int[imageFiles.length];

	        int counter = 0;
	        int label;

	        IplImage img;
	        IplImage grayImg;

	        for (File image : imageFiles) {
	            img = cvLoadImage(image.getAbsolutePath());

	            label = Integer.parseInt(image.getName().split("\\-")[0]);

	            grayImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 1);

	            cvCvtColor(img, grayImg, CV_BGR2GRAY);

	            images.put(counter, grayImg);

	            labels[counter] = label;

	            counter++;
	        }

	        IplImage greyTestImage = IplImage.create(testImage.width(), testImage.height(), IPL_DEPTH_8U, 1);

//	        FaceRecognizer faceRecognizer = createFisherFaceRecognizer();
	         FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
//	         FaceRecognizer faceRecognizer = createLBPHFaceRecognizer();

	        faceRecognizer.train(images, labels);

	        cvCvtColor(testImage, greyTestImage, CV_BGR2GRAY);

	        int predictedLabel = faceRecognizer.predict(greyTestImage);

	        System.out.println("Predicted label: " + predictedLabel);
	 

	}

}
