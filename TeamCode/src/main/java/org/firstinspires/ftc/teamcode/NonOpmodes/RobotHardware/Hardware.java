package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware;



import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.*;

import org.firstinspires.ftc.teamcode.NonOpmodes.Webcam.CVMaster;

import java.util.List;


public class Hardware{

    //Webcam---------------------
    public CVMaster webcam = new CVMaster();
    //---------------------------

    //Robot Hardware-------------
    public DcMotor frontLeft, frontRight, backLeft, backRight, intake;
    public DcMotorEx lift, extendoL, extendoR;

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
        intake = hardwareMap.get(DcMotor.class, EXMOTOR_1);
        intakeArm = hardwareMap.get(Servo.class, EXSERVO_5);
    }
    public void initExtension(HardwareMap hw){
        extendoL = hw.get(DcMotorEx.class, CHMOTOR_1);
        extendoR = hw.get(DcMotorEx.class, EXMOTOR_0);
        extendoL.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void initLift(HardwareMap hw){
        //Insert code to init pickup hardware
        lift = hw.get(DcMotorEx.class, CHMOTOR_2);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void initDifferential(HardwareMap hw){
        diffL = hw.get(Servo.class, EXSERVO_2);
        diffR = hw.get(Servo.class, EXSERVO_3);
        diffL.setDirection(Servo.Direction.REVERSE);
    }
    public void initFourBar(HardwareMap hw){
        fourBarL = hw.get(Servo.class, EXSERVO_0);
        fourBarR = hw.get(Servo.class, EXSERVO_1);
        fourBarL.setDirection(Servo.Direction.REVERSE);
    }

    public void initClaw(HardwareMap hw){
        claw = hw.get(Servo.class, EXSERVO_4);
    }

    public void initLynxModule(HardwareMap hardwareMap){
        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs){
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        }
    }


