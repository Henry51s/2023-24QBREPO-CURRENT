package org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.INTAKE_ARM_FIFTH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.INTAKE_ARM_FOURTH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.INTAKE_ARM_GROUND;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.INTAKE_ARM_SECOND;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.INTAKE_ARM_THIRD;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.INTAKE_MAX_POWER;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

public class Intake {
    public DcMotor intake;
    public Servo intakeArm;
    Hardware hardware = new Hardware();
    ElapsedTime timer = new ElapsedTime();

    public enum IntakeState{
        STOP,
        NORMAL,
        REVERSED
    }
    public enum IntakeArmState{
        GROUND,
        SECOND,
        THIRD,
        FOURTH,
        FIFTH
    }
    IntakeArmState intakeArmState = IntakeArmState.GROUND;
    IntakeState intakeState = IntakeState.NORMAL;

    public Intake(HardwareMap hw){
        hardware.initIntake(hw);
        intake = hardware.intake;
        intakeArm = hardware.intakeArm;
        //setIntakeArmState(IntakeArmState.GROUND);
    }

    public void setIntakeState(IntakeState state) {
        intakeState = state;
        switch(state){
            case STOP:
                intake.setPower(0);
                break;
            case REVERSED:
                intake.setPower(INTAKE_MAX_POWER);
                break;
            case NORMAL:
                intake.setPower(-INTAKE_MAX_POWER);
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
        }
    }

    public void runIntakeSetTime(int milliseconds){

        Thread intakeThread = new Thread(() -> {

            timer.reset();
            while (timer.milliseconds() <= milliseconds) {
                setIntakeState(IntakeState.NORMAL);
                // You might want to add a small delay here to avoid busy waiting
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            setIntakeState(IntakeState.STOP);
        });
        intakeThread.start();
    }
    public void loopIntake(Gamepad gamepad){
        if(gamepad.left_bumper){
            setIntakeState(IntakeState.NORMAL);
        }
        if(gamepad.right_bumper){
            setIntakeState(IntakeState.REVERSED);
        }
        if (!gamepad.right_bumper && !gamepad.left_bumper){
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
}
