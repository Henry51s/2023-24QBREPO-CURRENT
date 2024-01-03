package org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.CLAW_LATCH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.CLAW_RELEASE;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

public class Claw {
    Servo claw;
    Hardware hardware = new Hardware();

    public enum ClawState{
        OPEN,
        CLOSE
    }
    ClawState clawState = ClawState.CLOSE;
    public Claw(HardwareMap hw){
        hardware.initDeposit(hw);
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
