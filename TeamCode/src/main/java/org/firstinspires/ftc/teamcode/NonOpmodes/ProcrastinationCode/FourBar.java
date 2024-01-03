package org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_INIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_INTERMEDIATE;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_PICKUP;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;



public class FourBar {
    Servo v4bL, v4bR;
    Hardware hardware = new Hardware();

    public enum FourBarState {
        INIT,
        PICKUP,
        INTERMEDIATE,
        DEPOSIT
    }
    FourBarState fourBarState = FourBarState.INIT;


    public FourBar(HardwareMap hw){
        hardware.initDeposit(hw);
        v4bL = hardware.v4bL;
        v4bR = hardware.v4bR;
    }
    public void setV4BPosition(double position){
        v4bL.setPosition(position);
        v4bR.setPosition(position);
    }
    public void setV4bState(FourBarState state){
        fourBarState = state;
        switch (state){
            case INIT:
                setV4BPosition(V4B_INIT);
                break;
            case PICKUP:
                setV4BPosition(V4B_PICKUP);
                break;
            case INTERMEDIATE:
                setV4BPosition(V4B_INTERMEDIATE);
                break;
            case DEPOSIT:
                setV4BPosition(V4B_DEPOSIT);
                break;
        }
    }
    public double getPosition(){
        return v4bL.getPosition();
    }
    public FourBarState getFourBarState(){
        return fourBarState;
    }
}

