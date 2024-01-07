package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware;



import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.*;
import static org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests.LiftTestConstants.POWER;

import org.firstinspires.ftc.teamcode.NonOpmodes.Webcam.CVMaster;

import java.util.List;


public class Hardware{
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
    public DcMotorEx lift;

    public DcMotorEx extendoL, extendoR;
    public Servo diffL, diffR, fourBarL, fourBarR, claw, intakeArm;

    //---------------------------
    public void initDrive(HardwareMap hardwareMap){
        frontLeft = hardwareMap.get(DcMotor.class, CHMOTOR_0);
        frontRight = hardwareMap.get(DcMotor.class, EXMOTOR_2);
        backLeft = hardwareMap.get(DcMotor.class, CHMOTOR_3);
        backRight = hardwareMap.get(DcMotor.class, EXMOTOR_3);

        //For intake side forward, reverse frontRight and backRight
        //For Outtake side forward, reverse

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);



    }
    public void initIntake(HardwareMap hardwareMap){
        //Insert code to init intake motor + anything else
        //intakeArm = hardwareMap.get(Servo.class, );
        intake = hardwareMap.get(DcMotor.class, EXMOTOR_1);//WHICH MOTOR IS INTAKE???
        extendoL = hardwareMap.get(DcMotorEx.class, CHMOTOR_1);
        extendoR = hardwareMap.get(DcMotorEx.class, EXMOTOR_0);

        extendoL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendoR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendoL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extendoR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extendoL.setDirection(DcMotorSimple.Direction.REVERSE);


        intakeArm = hardwareMap.get(Servo.class, EXSERVO_5);

    }
    public void initDeposit(HardwareMap hardwareMap){
        //Insert code to init pickup hardware
        lift = hardwareMap.get(DcMotorEx.class, CHMOTOR_2);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        diffL = hardwareMap.get(Servo.class, EXSERVO_2);
        diffR = hardwareMap.get(Servo.class, EXSERVO_3);
        diffL.setDirection(Servo.Direction.REVERSE);

        fourBarL = hardwareMap.get(Servo.class, EXSERVO_0);
        fourBarR = hardwareMap.get(Servo.class, EXSERVO_1);
        fourBarL.setDirection(Servo.Direction.REVERSE);

        claw = hardwareMap.get(Servo.class, EXSERVO_4);
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

        frontLeft.setPower(y - x + rx);
        backLeft.setPower(y + x + rx);
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

