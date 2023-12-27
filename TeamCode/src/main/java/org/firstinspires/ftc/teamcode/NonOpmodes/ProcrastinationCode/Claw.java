package org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.CLAW_LATCH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.CLAW_RELEASE;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

public class Claw {
    Servo claw1, claw2;
    Hardware hardware = new Hardware();

    public enum ClawState{
        OPEN,
        CLOSE
    }
    ClawState clawState = ClawState.CLOSE;
    public Claw(HardwareMap hw){
        hardware.initDeposit(hw);
        //claw1 = hardware.claw1;
        //claw2 = hardware.claw2;
    }

    public void setClawState(ClawState state){
        clawState = state;
        switch(state){
            case OPEN:
                claw1.setPosition(CLAW_RELEASE);
                claw2.setPosition(CLAW_RELEASE);
                break;
            case CLOSE:
                claw1.setPosition(CLAW_LATCH);
                claw2.setPosition(CLAW_LATCH);
                break;
        }
    }
}
