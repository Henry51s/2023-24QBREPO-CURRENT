package org.firstinspires.ftc.teamcode.NonOpmodes;



import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.*;

import java.util.List;


public class Hardware{
    /*
    TO DO:
    -Odo Pod Config Names

    */

    //Webcam---------------------
    public CVMaster webcam = new CVMaster();
    //---------------------------

    //Robot Hardware-------------
    public DcMotor frontLeft, frontRight, backLeft, backRight, slide;
    public Servo linearLeft, linearRight,
    secArmL, secArmR, primArmL, primArmR, claw1, claw2
    ;
    public CRServo intL, intR;


    //---------------------------


    public void initDrive(HardwareMap hardwareMap){
        frontLeft = hardwareMap.get(DcMotor.class, MOTOR_0);
        frontRight = hardwareMap.get(DcMotor.class, MOTOR_1);
        backLeft = hardwareMap.get(DcMotor.class, MOTOR_2);
        backRight = hardwareMap.get(DcMotor.class, MOTOR_3);

        //For intake side forward, reverse frontRight and backRight
        //For Outtake side forward, reverse

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);



    }
    public void initIntake(HardwareMap hardwareMap){
        //Insert code to init intake motor + anything else
        intL = hardwareMap.get(CRServo.class, SERVO_1);
        intR = hardwareMap.get(CRServo.class, SERVO_7);
        linearLeft = hardwareMap.get(Servo.class, SERVO_0);
        linearRight = hardwareMap.get(Servo.class, SERVO_6);
        intR.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void initPickup(HardwareMap hardwareMap){
        //Insert code to init pickup hardware
        slide = hardwareMap.get(DcMotor.class, MOTOR_6);
        slide.setDirection(DcMotorSimple.Direction.REVERSE);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setTargetPosition(0);
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide.setPower(0.6);

        secArmL = hardwareMap.get(Servo.class, SERVO_2);
        secArmR = hardwareMap.get(Servo.class, SERVO_8);
        secArmL.setDirection(Servo.Direction.REVERSE);


        primArmL = hardwareMap.get(Servo.class, SERVO_4);
        primArmR = hardwareMap.get(Servo.class, SERVO_10);
        primArmL.setDirection(Servo.Direction.REVERSE);

        claw1 = hardwareMap.get(Servo.class, SERVO_9);
        claw1.setDirection(Servo.Direction.REVERSE);
        claw2 = hardwareMap.get(Servo.class, SERVO_11);
        //DONT FORGET TO REVERSE DIRECTIONS
    }
    public void initSensors(HardwareMap hardwareMap){
        //Insert code to init sensors
        webcam.initCamera(hardwareMap, WEBCAM);

    }
    public void initLynxModule(HardwareMap hardwareMap){
        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs){
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

    }
    public void initRobot(HardwareMap hardwareMap){
        //Run all init methods
        initDrive(hardwareMap);
        initIntake(hardwareMap);
        initPickup(hardwareMap);
    }
}

