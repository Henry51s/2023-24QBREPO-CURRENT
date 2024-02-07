package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals;

import com.acmerobotics.dashboard.config.Config;

import org.openftc.easyopencv.OpenCvCameraRotation;

@Config
public class GlobalVars {




    public static double CLIMBL_RELEASE = 0.68;
    public static double CLIMBR_RELEASE = 0.2;

    public static double CLIMBL_LATCH = 0.787;
    public static double CLIMBR_LATCH = 0.381;

    public static double DRONE_LATCH = 0.571;
    public static double DRONE_RELEASE = 0.038;


    public static int EXTENDO_MAX_VELOCITY = 900;
    public static int EXTENDO_RETRACTED = 0;
    public static int EXTENDO_CLIMB = 142;
    public static int EXTENDO_SHORT = 0;
    public static int EXTENDO_MED = 500;
    public static int EXTENDO_FAR = 900;

    public static double INTAKE_MAX_POWER = 0.75;


    public static double INTAKE_ARM_GROUND = 0.215;
    public static double INTAKE_ARM_SPIKEMARK = 0.145;

    //public static double INTAKE_ARM_SPIKEMARK = 0.145; //0.145
    public static double INTAKE_ARM_SECOND = 0.282;
    public static double INTAKE_ARM_THIRD = 0.357;
    public static double INTAKE_ARM_FOURTH = 0.366;
    public static double INTAKE_ARM_FIFTH = 0.462;
    public static double INTAKE_ARM_INIT = 0.993;

    public static double LIFT_MAX_POWER = 0.30;
    public static int LIFT_RETRACTED = 1;
    public static int LIFT_LOW = 583;
    public static int LIFT_MED = 1166;
    public static int LIFT_HIGH = 1750;

    public static int LIFT_AUTO_LOW = 400;


    public static double FOURBAR_INIT = 0.8;
    public static double FOURBAR_PICKUP = 0.913;
    public static double FOURBAR_DEPOSIT = 0.508;
    public static double FOURBAR_INTERMEDIATE = 0.663;

    public static double DIFFL_PICKUP = 0.152;
    public static double DIFFL_DEPOSIT = 0.987;
    public static double DIFFL_INTERMEDIATE = 0.287;
    public static double DIFFL_INIT = 0.304;

    public static double DIFFR_INTERMEDIATE = 0.255;
    public static double DIFFR_PICKUP = 0.152;
    public static double DIFFR_DEPOSIT = 0.411999;
    public static double DIFFR_INIT = 0.312;



    public static double CLAW_RELEASE = 0.338;
    public static double CLAW_LATCH = 0.739;
    public static double CLAW_LATCH_ONE_PIXEL = 0.772;



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
    public static final int xResolution = 800; //1280
    public static final int yResolution = 448; //720

    public static final int dashboardStreamFps = 5;







}
