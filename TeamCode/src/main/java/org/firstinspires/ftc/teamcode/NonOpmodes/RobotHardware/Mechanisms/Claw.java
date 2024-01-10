package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.CLAW_LATCH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.CLAW_RELEASE;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;

public class Claw {
    private static Claw instance;
    public static Claw getInstance(){
        if(instance == null){
            instance = new Claw();
        }
        return instance;
    }
    private Servo claw;
    private Hardware hardware = new Hardware();

    public enum ClawState{
        OPEN,
        CLOSE
    }
    ClawState clawState = ClawState.CLOSE;
    /*public Claw(HardwareMap hw){
        hardware.initClaw(hw);
        claw = hardware.claw;
    }*/
    private Claw(){

    }

    public void initClaw(HardwareMap hw){
        hardware.initClaw(hw);
        claw = hardware.claw;
    }
    public void setClawState(ClawState state){
        clawState = state;
        switch(state){
            case OPEN:
                claw.setPosition(CLAW_RELEASE);
                break;
            case CLOSE:
                claw.setPosition(CLAW_LATCH);
                break;
        }
    }
    public void setClawPosition(double position){
        claw.setPosition(position);
    }
    public double getClawPosition(){
        return claw.getPosition();
    }
    public ClawState getClawState(){
        return clawState;
    }
}
