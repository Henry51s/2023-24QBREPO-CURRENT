package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.INTAKE_ARM_FIFTH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.INTAKE_ARM_FOURTH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.INTAKE_ARM_GROUND;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.INTAKE_ARM_INIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.INTAKE_ARM_SECOND;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.INTAKE_ARM_SPIKEMARK;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.INTAKE_ARM_THIRD;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.INTAKE_MAX_POWER;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;

@Config
public class Intake {
    private static Intake instance;
    public static Intake getInstance(){
        if(instance == null){
            instance = new Intake();
        }
        return instance;
    }
    public DcMotorEx intake;
    public Servo intakeArm;
    private Hardware hardware = new Hardware();
    private ElapsedTime timer = new ElapsedTime();

    public enum IntakeState{
        STOP,
        NORMAL,
        REVERSED,
        RUN_TO_POSITION

    }

    public enum IntakeArmState{
        GROUND,
        SECOND,
        THIRD,
        FOURTH,
        FIFTH,
        INIT,
        SPIKEMARK
    }

    IntakeArmState intakeArmState = IntakeArmState.GROUND;
    IntakeState intakeState = IntakeState.NORMAL;

    int ticksPerRevolution = 145;
    public double rollCounter = 0;
    public double roundedRollCounter = 0;
    public static double increment = 0.1;
    public double targetPosition = 0;



    Gamepad current = new Gamepad(), previous = new Gamepad();

    public void initIntake(HardwareMap hw){
        hardware.initIntake(hw);
        intake = hardware.intake;
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setTargetPosition(0);
        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intake.setPower(INTAKE_MAX_POWER);

        targetPosition = 0;
        rollCounter = 0;
        roundedRollCounter = 0;

        intakeArm = hardware.intakeArm;
    }

    public void setIntakeState(IntakeState state) {

        intakeState = state;
        switch(state){
            case STOP:
                intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                intake.setPower(0);
                break;
            case REVERSED:
                intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                intake.setPower(-INTAKE_MAX_POWER);
                break;
            case NORMAL:
                intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                intake.setPower(INTAKE_MAX_POWER);
                break;

        }
    }
    public void setIntakeArmState(IntakeArmState state) {
        intakeArmState = state;
        switch (state) {
            case GROUND:
                intakeArm.setPosition(INTAKE_ARM_GROUND);
                break;
            case SECOND:
                intakeArm.setPosition(INTAKE_ARM_SECOND);
                break;
            case THIRD:
                intakeArm.setPosition(INTAKE_ARM_THIRD);
                break;
            case FOURTH:
                intakeArm.setPosition(INTAKE_ARM_FOURTH);
                break;
            case FIFTH:
                intakeArm.setPosition(INTAKE_ARM_FIFTH);
                break;
            case INIT:
                intakeArm.setPosition(INTAKE_ARM_INIT);
                break;
            case SPIKEMARK:
                intakeArm.setPosition(INTAKE_ARM_SPIKEMARK);
                break;
        }
    }

    /*public void runIntakeSetTime(int milliseconds, boolean reversed){
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        int directionConstant = 1;
        if(reversed){
            directionConstant = -1;
        }

        timer.reset();
        while(timer.milliseconds() < milliseconds){
            intake.setPower(-INTAKE_MAX_POWER*directionConstant);
        }
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setIntakeState(IntakeState.STOP);
    }*/
    public void runIntakeSetTime(int numberOfSpins, boolean reversed, double power){
        int motorDirection = 1;
        if(reversed){
            motorDirection = -1;
        }
        targetPosition += motorDirection * ticksPerRevolution * numberOfSpins;
        roundedRollCounter += motorDirection * numberOfSpins;
        rollCounter = roundedRollCounter;
        intake.setPower(power);
        intake.setTargetPosition((int) targetPosition);

    }    public void runIntakeSetTimeAsync(int milliseconds, boolean reversed){
        int motorDirection = 1;
        if(reversed)
            motorDirection = -1;
        int finalMotorDirection = motorDirection;
        Thread intakeThread = new Thread(() -> {

            timer.reset();
            while (timer.milliseconds() <= milliseconds) {
                intake.setPower(-INTAKE_MAX_POWER/2* finalMotorDirection);
                // You might want to add a small delay here to avoid busy waiting
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            intakeState = IntakeState.STOP;
        });
        intakeThread.start();
    }


    public void loopIntake(Gamepad gamepad){

        /*previous.copy(current);
        current.copy(gamepad);

        if(gamepad.left_bumper){
            rollCounter = rollCounter + increment;
        }
        else if(gamepad.right_bumper){
            rollCounter = rollCounter - increment;
        }
        else{
            rollCounter = Math.round(rollCounter/increment)*increment;
        }
        roundedRollCounter = Math.ceil(rollCounter);
        targetPosition = roundedRollCounter*ticksPerRevolution;

        intake.setTargetPosition((int) targetPosition);*/
        if(gamepad.left_bumper){
            setIntakeState(IntakeState.NORMAL);
        }
        else if(gamepad.right_bumper){
            setIntakeState(IntakeState.REVERSED);
        }
        else{
            setIntakeState(IntakeState.STOP);
        }
    }
    public void setArmPosition(double position){
        intakeArm.setPosition(position);
    }
    public IntakeState getIntakeState(){
        return intakeState;
    }
    public IntakeArmState getIntakeArmState(){
        return intakeArmState;
    }
    public double getIntakeArmPosition(){
        return intakeArm.getPosition();
    }
    public int getIntakePosition(){
        return intake.getCurrentPosition();
    }
}
