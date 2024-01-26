package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.INTAKE_ARM_FIFTH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.INTAKE_ARM_FOURTH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.INTAKE_ARM_GROUND;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.INTAKE_ARM_SECOND;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.INTAKE_ARM_THIRD;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.INTAKE_MAX_POWER;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;

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
        FIFTH
    }

    IntakeArmState intakeArmState = IntakeArmState.GROUND;
    IntakeState intakeState = IntakeState.NORMAL;

    /*public Intake(HardwareMap hw){
        hardware.initIntake(hw);
        intake = hardware.intake;
        intakeArm = hardware.intakeArm;
        //setIntakeArmState(IntakeArmState.GROUND);
    }*/

    int ticksPerRevolution = 145;
    int currentPosition = 0;

    public int targetPosition = 0;
    int threshold = 3;

    //getPosition % fullRevolution

    Gamepad current = new Gamepad(), previous = new Gamepad();

    public void initIntake(HardwareMap hw){
        hardware.initIntake(hw);
        intake = hardware.intake;
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setTargetPosition(0);
        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intake.setPower(1);

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
            case RUN_TO_POSITION:
                targetPosition = (int) (Math.ceil(currentPosition / ticksPerRevolution) * ticksPerRevolution);
                intake.setTargetPosition(targetPosition);
                intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                intake.setPower(1);

                while(Math.abs(targetPosition - intake.getCurrentPosition()) >= threshold){
                    intake.setTargetPosition(targetPosition);
                }
                setIntakeState(IntakeState.STOP);
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

    public void runIntakeSetTime(int milliseconds, boolean reversed){
        int directionConstant = 1;
        if(reversed){
            directionConstant = -1;
        }

        timer.reset();
        while(timer.milliseconds() < milliseconds){
            intake.setPower(-INTAKE_MAX_POWER*directionConstant);
        }
        setIntakeState(IntakeState.STOP);
    }
    public void runIntakeSetTimeAsync(int milliseconds, boolean reversed){
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
        previous.copy(current);
        current.copy(gamepad);

        if(gamepad.left_bumper){
            setIntakeState(IntakeState.NORMAL);
        }
        if(gamepad.right_bumper){
            setIntakeState(IntakeState.REVERSED);
        }
        if (!gamepad.left_bumper && !gamepad.right_bumper) {
            if(intakeState != IntakeState.RUN_TO_POSITION)
                setIntakeState(IntakeState.STOP);
        }
        if (current.dpad_up && !previous.dpad_up){
            currentPosition = intake.getCurrentPosition();
            setIntakeState(IntakeState.RUN_TO_POSITION);
        }
        //if(gamepad.back){
        //    currentPosition = intake.getCurrentPosition();
        //    setIntakeState(IntakeState.RUN_TO_POSITION);
        //}
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
