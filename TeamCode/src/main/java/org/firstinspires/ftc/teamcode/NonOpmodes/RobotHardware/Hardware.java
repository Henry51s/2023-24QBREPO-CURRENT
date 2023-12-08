package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware;



import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.*;

import org.firstinspires.ftc.teamcode.NonOpmodes.Webcam.CVMaster;

import java.util.List;


public class Hardware{
    /*
    TO DO:
    -Odo Pod Config Names

    */
    public enum DriveState{
        NORMAL, //Outtake = forward
        REVERSED //Intake = forward
    }
    DriveState driveState = DriveState.NORMAL;

    double flipMultiplier = 1;

    //Webcam---------------------
    public CVMaster webcam = new CVMaster();
    //---------------------------

    //Robot Hardware-------------
    public DcMotor frontLeft, frontRight, backLeft, backRight, intake;
    public PIDMotor lift;
    public Servo linearLeft, linearRight, diffL, diffR, v4bL, v4bR, claw1, claw2
    ;

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
        linearLeft = hardwareMap.get(Servo.class, SERVO_0);
        linearRight = hardwareMap.get(Servo.class, SERVO_6);
        intake = hardwareMap.get(DcMotor.class, MOTOR_5);//WHICH MOTOR IS INTAKE???
    }
    public void initDeposit(HardwareMap hardwareMap){
        //Insert code to init pickup hardware

        /*slide = hardwareMap.get(DcMotor.class, MOTOR_6);
        slide.setDirection(DcMotorSimple.Direction.REVERSE);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setTargetPosition(0);
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide.setPower(0.6);
        */
        lift = new PIDMotor(hardwareMap, MOTOR_6);


        diffL = hardwareMap.get(Servo.class, SERVO_2);
        diffR = hardwareMap.get(Servo.class, SERVO_8);
        diffL.setDirection(Servo.Direction.REVERSE);

        v4bL = hardwareMap.get(Servo.class, SERVO_4);
        v4bR = hardwareMap.get(Servo.class, SERVO_10);
        v4bL.setDirection(Servo.Direction.REVERSE);

        claw1 = hardwareMap.get(Servo.class, SERVO_9);
        claw1.setDirection(Servo.Direction.REVERSE);
        claw2 = hardwareMap.get(Servo.class, SERVO_11);
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
        webcam.initCamera(hardwareMap, WEBCAM);
        initDrive(hardwareMap);
        initIntake(hardwareMap);
        initDeposit(hardwareMap);
    }
    public void loopDrive(Gamepad gamepad){

        double y = -gamepad.left_stick_y * flipMultiplier; // Remember, Y stick is reversed!
        double x = gamepad.left_stick_x * flipMultiplier;
        double rx = -gamepad.right_stick_x*0.5;

        frontLeft.setPower(y + x + rx);
        backLeft.setPower(y - x + rx);
        frontRight.setPower(y - x - rx);
        backRight.setPower(y + x - rx);

        switch(driveState){
            case NORMAL:
                if(gamepad.left_stick_button){
                    flipMultiplier = 1;
                }
                if(gamepad.right_stick_button){
                    driveState = DriveState.REVERSED;
                }
                break;
            case REVERSED:
                if(gamepad.right_stick_button){
                    flipMultiplier = -1;
                }
                if(gamepad.left_stick_button){
                    driveState = DriveState.NORMAL;
                }
                break;

        }
    }
}

