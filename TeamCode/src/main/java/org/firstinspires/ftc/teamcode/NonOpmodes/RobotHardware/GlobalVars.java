package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware;

import com.acmerobotics.dashboard.config.Config;

import org.openftc.easyopencv.OpenCvCameraRotation;

@Config
public class GlobalVars {



    public static double INTAKE_MAX_POWER = 0.65;


    public static double p = 0;

    public static double i = 0;
    public static double d = 0;
    public static double TOLERANCE = 0;
    public static int EXTENDO_RETRACTED = 0;
    public static int EXTENDO_SHORT = 0;
    public static int EXTENDO_MED = 0;
    public static int EXTENDO_FAR = 0;


    public static double INTAKE_ARM_GROUND = 0.245;
    public static double INTAKE_ARM_SECOND = 0.282;
    public static double INTAKE_ARM_THIRD = 0.357;
    public static double INTAKE_ARM_FOURTH = 0.366;
    public static double INTAKE_ARM_FIFTH = 0.462;

    public static int LIFT_RETRACTED = 5;
    public static int LIFT_LOW = 500;
    public static int LIFT_MED = 1000;
    public static int LIFT_HIGH = 0;



    public static double FOURBAR_INIT = 0.533;
    public static double FOURBAR_PICKUP = 0.759;
    public static double FOURBAR_DEPOSIT = 0.3739;
    public static double FOURBAR_INTERMEDIATE = 0.6;

    public static double DIFFL_PICKUP = 0.127;
    public static double DIFFL_DEPOSIT = 0.386;

    public static double DIFFL_INTERMEDIATE = 0.1349;
    public static double DIFFR_PICKUP = 0.107;
    public static double DIFFR_DEPOSIT = 0.90;

    public static double DIFFR_INTERMEDIATE = 0.1109;

    public static double CLAW_RELEASE = 0.150;
    public static double CLAW_LATCH = 0.393;

    //Config Names-----------------

    public static String CHMOTOR_0 = "motor0"; //Front Left
    public static String CHMOTOR_1 = "motor1"; //Front Right
    public static String CHMOTOR_2 = "motor2"; //Back Left
    public static String CHMOTOR_3 = "motor3"; //Back Right
    public static String EXMOTOR_0 = "motor4";
    public static String EXMOTOR_1 = "motor5";
    public static String EXMOTOR_2 = "motor6";
    public static String EXMOTOR_3 = "motor7";

    //Control Hub Servos 0-5 = 0-5, Expansion Hub Servos 0-5 = 6-11
    public static String CHSERVO_0 = "servo0"; //Linear Servo Left (Positional)
    public static String CHSERVO_1 = "servo1";//Intake Left (Continuous)
    public static String CHSERVO_2 = "servo2";//Differential Left (Positional)
    public static String CHSERVO_3 = "servo3";
    public static String CHSERVO_4 = "servo4";//V4B Left (Positional)
    public static String CHSERVO_5 = "servo5";
    public static String EXSERVO_0 = "servo6";//Linear Servo Right (Positional)
    public static String EXSERVO_1 = "servo7";//Intake Right (Continuous)
    public static String EXSERVO_2 = "servo8";//Differential Right (Positional)
    public static String EXSERVO_3 = "servo9";//claw 1 or 2
    public static String EXSERVO_4 = "servo10";//V4B Right (Positional)
    public static String EXSERVO_5 = "servo11";//Claw 1 or 2
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
