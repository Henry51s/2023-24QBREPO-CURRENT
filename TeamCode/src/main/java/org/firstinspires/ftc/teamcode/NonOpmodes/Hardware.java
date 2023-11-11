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
    public Servo arm,claw,linearLeft, linearRight;
    public CRServo intakeLeft, intakeRight;


    //---------------------------


    public void initDrive(HardwareMap hardwareMap){
        frontLeft = hardwareMap.get(DcMotor.class, MOTOR_0);
        frontRight = hardwareMap.get(DcMotor.class, MOTOR_1);
        backLeft = hardwareMap.get(DcMotor.class, MOTOR_2);
        backRight = hardwareMap.get(DcMotor.class, MOTOR_3);

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

    }
    public void initIntake(HardwareMap hardwareMap){
        //Insert code to init intake motor + anything else
        intakeLeft = hardwareMap.get(CRServo.class, SERVO_1);
        intakeRight = hardwareMap.get(CRServo.class, SERVO_7);
        linearLeft = hardwareMap.get(Servo.class, SERVO_0);
        linearRight = hardwareMap.get(Servo.class, SERVO_6);
        intakeRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void initPickup(HardwareMap hardwareMap){
        //Insert code to init pickup hardware
        slide = hardwareMap.get(DcMotor.class, MOTOR_6);
        slide.setDirection(DcMotorSimple.Direction.REVERSE);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setTargetPosition(0);
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide.setPower(0.6);
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

