package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.LIFT_HIGH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.LIFT_LOW;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.LIFT_MED;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.LIFT_RETRACTED;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class DepositSubsystem extends SubsystemBase {
    //Actuators
    private final PIDMotor lift;

    private final Servo v4bL, v4bR, diffL, diffR, claw1, claw2;

    private int targetPosition = LIFT_RETRACTED;

    //Create states for slide, v4b, diff, claw
    public enum LiftState{
        RETRACTED,
        LOW,
        MED,
        HIGH
    }
    public enum V4bState{
        INIT,
        PICKUP,
        DEPOSIT
    }
    public enum DiffState{
        INIT,
        PICKUP,
        DEPOSIT
    }
    public enum ClawState{
        OPEN,
        CLOSE
    }
    public LiftState liftState = LiftState.RETRACTED;
    public V4bState v4bState = V4bState.INIT;
    public DiffState diffState = DiffState.INIT;
    public ClawState clawState = ClawState.CLOSE;
    //Loop Functions for each subsystem, one overall loop for teleop

    Hardware hardware = new Hardware();

    public DepositSubsystem(HardwareMap hw){
        hardware.initDeposit(hw);
        lift = hardware.lift;
        v4bL = hardware.v4bL;
        v4bR = hardware.v4bR;
        diffL = hardware.diffL;
        diffR = hardware.diffR;
        claw1 = hardware.claw1;
        claw2 = hardware.claw2;
    }
    public void setLiftState(LiftState state){
        liftState = state;
        switch(state){
            case RETRACTED:
                lift.setTargetPosition(LIFT_RETRACTED);
                break;
            case LOW:
                lift.setTargetPosition(LIFT_LOW);
                break;
            case MED:
                lift.setTargetPosition(LIFT_MED);
                break;
            case HIGH:
                lift.setTargetPosition(LIFT_HIGH);
                break;
        }
    }
    public void setV4bState(V4bState state){ //v4b.setPosition(...)
        v4bState = state;
        switch (state){
            case INIT:

                break;
            case PICKUP:

                break;
            case DEPOSIT:

                break;
        }
    }
    public void setDiffState(DiffState state){
        diffState = state;
        switch(state){
            case INIT:

                break;
            case PICKUP:

                break;
            case DEPOSIT:

                break;
        }
    }
    public void setClawState(ClawState state){
        clawState = state;
        switch(state){
            case OPEN:

                break;
            case CLOSE:

                break;
        }
    }
    public void loop(){
        //Add PID Code
        lift.runToPos(); //Enables PID
    }
    public void read(){
        //Read Encoder/Servo Position???
    }


}
