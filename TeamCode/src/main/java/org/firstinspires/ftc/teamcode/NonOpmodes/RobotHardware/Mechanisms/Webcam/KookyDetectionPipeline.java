package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.xResolution;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.yResolution;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class KookyDetectionPipeline implements VisionProcessor {

    public enum Location{
        LEFT,
        CENTER,
        RIGHT
    }
    Location location = Location.LEFT;
    private final Mat hsv = new Mat();

    Mat leftZone;
    Mat centerZone;

    public Scalar left = new Scalar(0,0,0);
    public Scalar center = new Scalar(0,0,0);

    public double leftColor = 0.0;
    public double centerColor = 0.0;
    public static double threshold = 180;

    Telemetry telemetry;


    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        Rect leftZoneArea = new Rect(0,0,xResolution/2, yResolution);
        Rect centerZoneArea = new Rect(xResolution/2, 0, xResolution/2,yResolution);
        leftZone = frame.submat(leftZoneArea);
        centerZone = frame.submat(centerZoneArea);

        Imgproc.cvtColor(leftZone, leftZone, Imgproc.COLOR_RGB2HSV);
        Imgproc.cvtColor(centerZone, centerZone, Imgproc.COLOR_RGB2HSV);

        Imgproc.blur(leftZone, leftZone, new Size(5,5));
        Imgproc.blur(centerZone, centerZone, new Size(5,5));

        Core.inRange(leftZone, new Scalar(0,150,150), new Scalar(180,255,255), leftZone);
        Core.inRange(centerZone, new Scalar(0,150,150), new Scalar(180,255,255), centerZone);

        left = Core.mean(leftZone);
        center = Core.mean(centerZone);

        leftColor = left.val[0];
        centerColor = center.val[0];

        if(leftColor > threshold){
            location = Location.LEFT;
        }
        else if(centerColor > threshold){
            location = Location.CENTER;
        }
        else{
            location = Location.RIGHT;
        }
        if(telemetry != null){
            telemetry.addData("Left color: ", leftColor);
            telemetry.addData("centerColor: ", centerColor);
            telemetry.addData("Location: ", location);
        }
        leftZone.release();
        centerZone.release();




        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
    public Location getLocation(){
        return this.location;
    }
    public double[] getColorReadings(){
        double[] colorReadings = new double[2];
        colorReadings[0] = leftColor;
        colorReadings[1] = centerColor;

        return colorReadings;

    }
}
