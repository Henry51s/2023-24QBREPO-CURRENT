package org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_INIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_PICKUP;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;



public class FourBar {
    Servo v4bL, v4bR;
    Hardware hardware = new Hardware();

    public enum V4bState{
        INIT,
        PICKUP,
        DEPOSIT
    }
    V4bState v4bState = V4bState.INIT;


    public FourBar(HardwareMap hw){
        hardware.initDeposit(hw);
        v4bL = hardware.v4bL;
        v4bR = hardware.v4bR;
    }
    public void setV4BPosition(double position){
        v4bL.setPosition(position);
        v4bR.setPosition(position);
    }
    public void setV4bState(V4bState state){
        v4bState = state;
        switch (state){
            case INIT:
                setV4BPosition(V4B_INIT);
                break;
            case PICKUP:
                setV4BPosition(V4B_PICKUP);
                break;
            case DEPOSIT:
                setV4BPosition(V4B_DEPOSIT);
                break;
        }
    }
    public double getPosition(){
        return v4bL.getPosition();
    }
}

