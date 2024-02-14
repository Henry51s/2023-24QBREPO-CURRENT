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



    public enum IntakeState{
        STOP,
        NORMAL,
        REVERSED,
        HALF,
        REVERSED_HALF
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

    private IntakeArmState intakeArmState = IntakeArmState.GROUND;
    private IntakeState intakeState = IntakeState.NORMAL;
    private double armPosition = INTAKE_ARM_GROUND;

    private DcMotorEx intake;
    private Servo intakeArm;
    private Hardware hardware = new Hardware();
    private ElapsedTime timer = new ElapsedTime();


    private Gamepad current = new Gamepad(), previous = new Gamepad();
    public void initIntake(HardwareMap hw){
        hardware.initIntake(hw);
        intake = hardware.intake;
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setTargetPosition(0);
        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intake.setPower(INTAKE_MAX_POWER);

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
            case HALF:
                intake.setPower(INTAKE_MAX_POWER/2);
                break;
            case REVERSED_HALF:
                intake.setPower(-INTAKE_MAX_POWER/2);
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

    public void runIntakeSetTime(int milliseconds, IntakeState intakeState){
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        timer.reset();
        while(timer.milliseconds() < milliseconds){
            setIntakeState(intakeState);
        }
        setIntakeState(IntakeState.STOP);


    }
    public void runIntakeSetTimeAsync(int milliseconds, IntakeState intakeStateA){
        Thread intakeThread = new Thread(() -> {

            timer.reset();
            while (timer.milliseconds() <= milliseconds) {
                setIntakeState(intakeStateA);
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
        previous.copy(current);
        current.copy(gamepad);

        if(gamepad.left_trigger > 0){
            armPosition -= 0.05;
        }
        else if(gamepad.right_trigger>0){
            armPosition += 0.05;
        }
        armPosition = Math.max(INTAKE_ARM_GROUND, Math.min(armPosition, INTAKE_ARM_FIFTH));
        setArmPosition(armPosition);


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
