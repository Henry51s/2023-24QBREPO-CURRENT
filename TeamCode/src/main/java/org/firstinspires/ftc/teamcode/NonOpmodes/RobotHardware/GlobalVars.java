package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware;

import com.acmerobotics.dashboard.config.Config;

import org.openftc.easyopencv.OpenCvCameraRotation;

@Config
public class GlobalVars {



    public static double INTAKE_MAX_POWER = 0.65;

    public static int LIFT_RETRACTED = 0;
    public static int LIFT_LOW = 0;
    public static int LIFT_MED = 0;
    public static int LIFT_HIGH = 0;



    public static double V4B_INIT = 0.533;
    public static double V4B_PICKUP = 0.811;
    public static double V4B_DEPOSIT = 0.4139;

    public static double DIFFL_INIT = 0;
    public static double DIFFL_PICKUP = 0.268;
    public static double DIFFL_DEPOSIT = 0.892;
    public static double DIFFR_INIT = 0;
    public static double DIFFR_PICKUP = 0.03599;
    public static double DIFFR_DEPOSIT = 0.667;

    public static double CLAW_RELEASE = 0.46099;
    public static double CLAW_LATCH = 0.5;

    //Config Names-----------------
    //Control Hub Motors 0-3 = 0-3, Expansion Hub Motors 0-3 = 4-7
    public static String MOTOR_0 = "motor0"; //Front Left
    public static String MOTOR_1 = "motor1"; //Front Right
    public static String MOTOR_2 = "motor2"; //Back Left
    public static String MOTOR_3 = "motor3"; //Back Right
    public static String MOTOR_4 = "motor4";
    public static String MOTOR_5 = "motor5";
    public static String MOTOR_6 = "motor6";
    public static String MOTOR_7 = "motor7";

    //Control Hub Servos 0-5 = 0-5, Expansion Hub Servos 0-5 = 6-11
    public static String SERVO_0 = "servo0"; //Linear Servo Left (Positional)
    public static String SERVO_1 = "servo1";//Intake Left (Continuous)
    public static String SERVO_2 = "servo2";//Differential Left (Positional)
    public static String SERVO_3 = "servo3";
    public static String SERVO_4 = "servo4";//V4B Left (Positional)
    public static String SERVO_5 = "servo5";
    public static String SERVO_6 = "servo6";//Linear Servo Right (Positional)
    public static String SERVO_7 = "servo7";//Intake Right (Continuous)
    public static String SERVO_8 = "servo8";//Differential Right (Positional)
    public static String SERVO_9 = "servo9";//claw 1 or 2
    public static String SERVO_10 = "servo10";//V4B Right (Positional)
    public static String SERVO_11 = "servo11";//Claw 1 or 2

    public static String WEBCAM = "webcam";
    //Vision Constants-------------

    //Webcam Resolution
    public static final int xResolution = 800;
    public static final int yResolution = 448;

    public static final int dashboardStreamFps = 5;

    //Camera Orientation
    public static final OpenCvCameraRotation cameraOrientation = OpenCvCameraRotation.UPSIDE_DOWN;


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
