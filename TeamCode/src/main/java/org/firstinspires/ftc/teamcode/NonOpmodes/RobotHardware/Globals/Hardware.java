package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals;



import static com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.*;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Drive;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.FourBar;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.SideObjective;

import java.util.List;


public class Hardware{
    //Robot Hardware-------------
    public DcMotor frontLeft, frontRight, backLeft, backRight;
    public DcMotorEx lift, extendoL, extendoR, intake;
    public Servo diffL, diffR, fourBarL, fourBarR, claw, intakeArm, drone;
    public CRServo climbL, climbR;
    //---------------------------
    public Claw clawInstance;
    public Differential differentialInstance;
    public Drive driveInstance;
    public Extension extensionInstance;
    public FourBar fourBarInstance;
    public Intake intakeInstance;
    public Lift liftInstance;
    public SideObjective sideObjectiveInstance;


    private void getInstances(){
        clawInstance = Claw.getInstance();
        differentialInstance = Differential.getInstance();
        driveInstance = Drive.getInstance();
        extensionInstance = Extension.getInstance();
        fourBarInstance = FourBar.getInstance();
        intakeInstance = Intake.getInstance();
        liftInstance = Lift.getInstance();
        sideObjectiveInstance = SideObjective.getInstance();
    }
    public void initAll(HardwareMap hw){
        getInstances();
        clawInstance.initClaw(hw);
        differentialInstance.initDifferential(hw);
        driveInstance.initDrive(hw);
        extensionInstance.initExtension(hw);
        fourBarInstance.initFourBar(hw);
        intakeInstance.initIntake(hw);
        liftInstance.initLift(hw);
        sideObjectiveInstance.initSideQuest(hw);



    }
    public void initAuto(HardwareMap hw){
        getInstances();
        clawInstance.initClaw(hw);
        differentialInstance.initDifferential(hw);
        extensionInstance.initExtension(hw);
        fourBarInstance.initFourBar(hw);
        intakeInstance.initIntake(hw);
        liftInstance.initLift(hw);
        sideObjectiveInstance.initSideQuest(hw);
    }









    public void initClimbAndDrone(HardwareMap hw){
        climbL = hw.get(CRServo.class, CHSERVO_0);

        climbR = hw.get(CRServo.class, CHSERVO_1);
        climbR.setDirection(DcMotorSimple.Direction.REVERSE);

        drone = hw.get(Servo.class, CHSERVO_5);
        drone.setDirection(REVERSE);


    }
    public void initDrive(HardwareMap hw){
        backLeft = hw.get(DcMotorEx.class, CHMOTOR_0);
        frontRight = hw.get(DcMotorEx.class, EXMOTOR_2);
        frontLeft = hw.get(DcMotorEx.class, CHMOTOR_3);
        backRight = hw.get(DcMotorEx.class, EXMOTOR_3);


        frontLeft .setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);



    }
    public void initIntake(HardwareMap hw){
        intake = hw.get(DcMotorEx.class, EXMOTOR_1);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeArm = hw.get(Servo.class, EXSERVO_5);
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
        diffL.setDirection(REVERSE);
        //diffR.setDirection(REVERSE);
    }
    public void initFourBar(HardwareMap hw){
        fourBarL = hw.get(Servo.class, EXSERVO_0);
        fourBarR = hw.get(Servo.class, EXSERVO_1);
        fourBarL.setDirection(REVERSE);


    }

    public void initClaw(HardwareMap hw){
        claw = hw.get(Servo.class, EXSERVO_4);
    }
    }


