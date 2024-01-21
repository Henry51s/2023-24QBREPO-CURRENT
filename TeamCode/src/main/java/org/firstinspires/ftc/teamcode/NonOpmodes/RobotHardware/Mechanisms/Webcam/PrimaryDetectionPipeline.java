package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.xResolution;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.yResolution;

import com.acmerobotics.dashboard.config.Config;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Range;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

@Config
public class PrimaryDetectionPipeline extends OpenCvPipeline {

    public static double hueMin = 110;
    public static double hueMax = 130;
    public static double saturationMin = 120;
    public static double saturationMax = 250;
    public static double valueMin = 50;
    public static double valueMax = 255;

    private static double rValue = 200.0;
    private static double gValue = 50.0;
    private static double bValue = 200.0;

    public enum Color{
        BLUE,
        RED
    }
    Color color;

    public void initPipeline(Color color){
        this.color = color;
        switch(this.color){
            case RED:
                hueMin = 110;
                hueMax = 130;
                saturationMin = 120;
                saturationMax = 250;
                valueMin = 50;
                valueMax = 255;

                break;
            case BLUE:
                hueMin = 0;
                hueMax = 60;
                saturationMin = 180;
                saturationMax = 255;
                valueMin = 70;
                valueMax = 250;

                break;

        }
    }

    // Coordinate Locations for bounding boxes of each of the three individual "subviews" of the camera (left, center, right)
    public static int centerRowStart = xResolution/3;
    public static int centerRowEnd = 2*xResolution/3;
    public static int sideRowStart = 0;
    public static int sideRowEnd = xResolution/3;
    public static int leftColStart = 0;
    public static int leftColEnd = yResolution;
    public static int centerColStart = 0;
    public static int centerColEnd = yResolution;

    private int threshold = 400000;


    double leftTotal;
    double rightTotal;
    double centerTotal;

    // Define the enum for ItemLocation
    public enum ItemLocation {
        RIGHT, LEFT, CENTER
    }

    private ItemLocation location = ItemLocation.CENTER;
    private Mat workingMatrix = new Mat();
    private Mat matLeft = new Mat(), matCenter = new Mat();


    @Override
    public Mat processFrame(Mat input) {
        input.copyTo(workingMatrix);

        if (workingMatrix.empty()) {
            return input;
        }

        // Filter for color
        Imgproc.GaussianBlur(workingMatrix, workingMatrix, new Size(5.0, 15.0), 0.00);
        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_BGR2HSV);
        Core.inRange(workingMatrix, new Scalar(hueMin, saturationMin, valueMin),
                new Scalar(hueMax, saturationMax, valueMax), workingMatrix);

        // Create 3 areas to analyze
        workingMatrix.copyTo(matLeft);
        //workingMatrix.copyTo(matCenter);

        Rect roiLeft = new Rect(0, 0, xResolution/3, yResolution);
        Rect roiCenter = new Rect(xResolution/3, 0, 2*xResolution/3, yResolution);

        //Imgproc.resize(matLeft, matLeft, new Size(2*xResolution/3, yResolution));
       // matCenter = workingMatrix.submat(xResolution/2, Math.min(xResolution-1,workingMatrix.rows()), 0, Math.min(yResolution-1,workingMatrix.cols()));
        matLeft = workingMatrix.submat(roiLeft);
        matCenter = workingMatrix.submat(roiCenter);
        //matLeft = workingMatrix.submat(0, (xResolution/2)-1, 0, Math.min(leftColEnd-1,workingMatrix.cols()));
        //matCenter = workingMatrix.submat(xResolution/2, Math.min((xResolution)-1,workingMatrix.rows()), 0, Math.min(centerColEnd-1,workingMatrix.cols()));


        // Draw boxes on screen
        //Imgproc.rectangle(workingMatrix, new Point(0, 0), new Point(xResolution/2, yResolution), new Scalar(rValue, gValue, bValue), 2);
        //Imgproc.rectangle(workingMatrix, new Point(xResolution/2, 0), new Point(xResolution, yResolution), new Scalar(rValue, gValue, bValue), 2);

        Imgproc.rectangle(workingMatrix, roiLeft, new Scalar(rValue, gValue, bValue));
        Imgproc.rectangle(workingMatrix, roiCenter, new Scalar(rValue, gValue, bValue));

        // Calculate pixel density in each box
        //leftTotal = Core.sumElems(matLeft).val[0];
        leftTotal = ((matLeft.empty()) ? 0: Core.sumElems(matLeft).val[0]);
        //centerTotal = Core.sumElems(matCenter).val[0];
        centerTotal = ((matCenter.empty()) ? 0: Core.sumElems(matCenter).val[0]);

        matLeft.release();
        matCenter.release();



        // Compare to determine location

        if(leftTotal > centerTotal){
            location = ItemLocation.LEFT;
        }
        if(centerTotal > leftTotal){
            location = ItemLocation.CENTER;
        }
        if(centerTotal < threshold && leftTotal < threshold){
            location = ItemLocation.RIGHT;
        }

        /*if (leftTotal > centerTotal) {
            if (leftTotal > rightTotal) {
                location = ItemLocation.LEFT;
            } else {
                location = ItemLocation.RIGHT;
            }
        } else {
            if (centerTotal > rightTotal) {
                location = ItemLocation.CENTER;
            } else {
                location = ItemLocation.RIGHT;
            }
        }
*/
        return workingMatrix;
    }

    public double getLeftTotal() {
        return leftTotal;
    }

    public double getCenterTotal() {
        return centerTotal;
    }

    // Getter method for the 'location' variable
    public ItemLocation getLocation() {
        return location;
    }
    public double[] getMatInfo(){
        double[] matInfo = {matCenter.rows(), matCenter.cols()};
        return matInfo;
    }

}