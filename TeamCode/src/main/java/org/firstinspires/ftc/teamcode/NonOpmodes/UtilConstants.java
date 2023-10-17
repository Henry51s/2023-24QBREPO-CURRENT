package org.firstinspires.ftc.teamcode.NonOpmodes;

import com.acmerobotics.dashboard.config.Config;

import org.openftc.easyopencv.OpenCvCameraRotation;

@Config
public class UtilConstants {



    //Config Names-----------------
    public static String MOTOR_0 = "motor0"; //Front Left
    public static String MOTOR_1 = "motor1"; //Front Right
    public static String MOTOR_2 = "motor2"; //Back Left
    public static String MOTOR_3 = "motor3"; //Back Right
    public static String WEBCAM = "webcam";
    //Vision Constants-------------

    //Webcam Resolution
    public static final int xResolution = 800;
    public static final int yResolution = 448;

    public static final int dashboardStreamFps = 5;

    //Camera Orientation
    public static final OpenCvCameraRotation cameraOrientation = OpenCvCameraRotation.UPRIGHT;


    //Lower bound yCbCr values for desired color
    public static int lowerY = 73;
    public static int lowerCb = 160;
    public static int lowerCr = 59;

    //Upper bound yCbCr values for desired color
    public static int upperY = 255;
    public static int upperCb = 188;
    public static int upperCr = 98;

    //Dimensions for region of interest rectangle
    public static int x1 = 0;
    public static int y1 = 0;
    public static int w = 800;
    public static int h = 448;
    //-----------------------------



}
