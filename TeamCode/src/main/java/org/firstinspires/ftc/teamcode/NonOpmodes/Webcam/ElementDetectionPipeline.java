package org.firstinspires.ftc.teamcode.NonOpmodes.Webcam;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.xResolution;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.yResolution;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class ElementDetectionPipeline implements VisionProcessor {

    public enum DetectionLocation{
        LEFT,
        CENTER,
        RIGHT
    }
    public enum ElementColor{
        BLUE,
        RED
    }
    DetectionLocation detectionLocation = DetectionLocation.LEFT;
    ElementColor elementColor = ElementColor.RED;

    private Mat testMat = new Mat();
    private Mat highMat = new Mat();
    private Mat lowMat = new Mat();
    private Mat finalMat = new Mat();

    static private Scalar lowHSVColorLower, lowHSVColorUpper; //Beginning of Color Wheel
    static private Scalar highHSVColorLower, highHSVColorUpper; //Wraps around Color Wheel


    private static final double colorThreshold = 0.5;

    static final Rect LEFT_RECTANGLE = new Rect(
            new Point(0,0),
            new Point(xResolution/3,yResolution)
    );
    static final Rect MIDDLE_RECTANGLE = new Rect(
            new Point(xResolution/3, 0),
            new Point(2*xResolution/3, yResolution)
    );
    static final Rect RIGHT_RECTANGLE = new Rect(
            new Point(2*xResolution/3, 0),
            new Point(xResolution, yResolution)
    );

    public ElementDetectionPipeline(ElementColor elementColor){
        this.elementColor = elementColor;
    }

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        if(elementColor == ElementColor.RED){
            lowHSVColorLower = new Scalar(0, 100, 20);

            lowHSVColorUpper = new Scalar(10, 255, 255);

            highHSVColorLower = new Scalar(160, 100, 20);

            highHSVColorUpper = new Scalar(180, 255, 255);
        }
        else if(elementColor == ElementColor.BLUE){
            //Tune scalar values
        }
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {

        Imgproc.cvtColor(frame, testMat, Imgproc.COLOR_RGB2HSV);


        Core.inRange(testMat, lowHSVColorLower, lowHSVColorUpper, lowMat);
        Core.inRange(testMat, highHSVColorLower, highHSVColorUpper, highMat);

        testMat.release();

        Core.bitwise_or(lowMat, highMat, finalMat);

        lowMat.release();
        highMat.release();

        double leftBox = Core.sumElems(finalMat.submat(LEFT_RECTANGLE)).val[0];
        double rightBox = Core.sumElems(finalMat.submat(RIGHT_RECTANGLE)).val[0];
        double middleBox = Core.sumElems(finalMat.submat(MIDDLE_RECTANGLE)).val[0];

        double averagedLeftBox = leftBox / LEFT_RECTANGLE.area() / 255;
        double averagedRightBox = rightBox / RIGHT_RECTANGLE.area() / 255; //Makes value [0,1]
        double averagedMiddleBox = middleBox / MIDDLE_RECTANGLE.area() / 255;




        if(averagedLeftBox > colorThreshold){        //Must Tune Red Threshold
            detectionLocation = DetectionLocation.LEFT;
        }else if(averagedRightBox> colorThreshold){
            detectionLocation = DetectionLocation.RIGHT;
        }else if(averagedMiddleBox > colorThreshold){
            detectionLocation = DetectionLocation.CENTER;
        }

        return null;            //You do not return the original mat anymore, instead return null
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
    public DetectionLocation getDetectionLocation(){
        return detectionLocation;
    }
}
