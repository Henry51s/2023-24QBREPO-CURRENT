package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam;

import com.acmerobotics.dashboard.config.Config;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

@Config
public class VisionPipelineBlue extends OpenCvPipeline {
    public static double hueMin = 0;
    public static double hueMax = 60;
    public static double saturationMin = 180;
    public static double saturationMax = 255;
    public static double valueMin = 70;
    public static double valueMax = 250;

    private static double rValue = 200.0;
    private static double gValue = 50.;
    private static double bValue = 200.0;

    // Coordinate Locations for bounding boxes of each of the three individual "subviews" of the camera (left, center, right)
    public static int centerRowStart = 300;
    public static int centerRowEnd = 700;
    public static int sideRowStart = 200;
    public static int sideRowEnd = 600;
    public static int leftColStart = 200;
    public static int leftColEnd = 500;
    public static int centerColStart = 810;
    public static int centerColEnd = 1110;
    public static int rightColStart = 1420;
    public static int rightColEnd = 1720;

    double leftTotal;
    double rightTotal;
    double centerTotal;


    // Define the enum for ItemLocation
    public enum ItemLocation {
        RIGHT, LEFT, CENTER
    }

    private static ItemLocation location = ItemLocation.CENTER;
    private Mat workingMatrix = new Mat();


    @Override
    public Mat processFrame(Mat input) {
        input.copyTo(workingMatrix);

        if (workingMatrix.empty()) {
            return input;
        }

        // filter for color
        Imgproc.GaussianBlur(workingMatrix, workingMatrix, new Size(5.0, 15.0), 0.00);
        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_BGR2HSV);
        Core.inRange(workingMatrix, new Scalar(hueMin, saturationMin, valueMin),
                new Scalar(hueMax, saturationMax, valueMax), workingMatrix);

        // create 3 areas to analyze
        Mat matLeft = workingMatrix.submat(sideRowStart, sideRowEnd, leftColStart, leftColEnd);
        Mat matCenter = workingMatrix.submat(centerRowStart, centerRowEnd, centerColStart, centerColEnd);
        Mat matRight = workingMatrix.submat(sideRowStart, sideRowEnd, rightColStart, rightColEnd);

        // draw boxes on screen
        Imgproc.rectangle(workingMatrix, new Point(leftColStart, sideRowStart), new Point(leftColEnd, sideRowEnd), new Scalar(rValue, gValue, bValue), 2);
        Imgproc.rectangle(workingMatrix, new Point(centerColStart, centerRowStart), new Point(centerColEnd, centerRowEnd), new Scalar(rValue, gValue, bValue), 2);
        Imgproc.rectangle(workingMatrix, new Point(rightColStart, sideRowStart), new Point(rightColEnd, sideRowEnd), new Scalar(rValue, gValue, bValue), 2);

        // calculate pixel density in each box
        leftTotal = Core.sumElems(matLeft).val[0];
        centerTotal = Core.sumElems(matCenter).val[0];
        rightTotal = Core.sumElems(matRight).val[0];

        // Compare to determine location
        if (leftTotal > centerTotal) {
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

        return workingMatrix;
    }

    public double getLeftTotal() {
        return leftTotal;
    }

    public double getCenterTotal() {
        return centerTotal;
    }

    public double getRightTotal() {
        return rightTotal;
    }

    // Getter method for the 'location' variable
    public ItemLocation getLocation() {
        return location;
    }
}