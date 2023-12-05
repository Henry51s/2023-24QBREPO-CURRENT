package org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_INIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_PICKUP;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.DepositSubsystem;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

import kotlin.collections.IndexingIterator;

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

    public void setV4bState(V4bState state){ //v4b.setPosition(...)
        v4bState = state;
        switch (state){
            case INIT:
                v4bL.setPosition(V4B_INIT);
                v4bR.setPosition(V4B_INIT);

                break;
            case PICKUP:
                v4bL.setPosition(V4B_PICKUP);
                v4bR.setPosition(V4B_PICKUP);
                break;
            case DEPOSIT:
                v4bL.setPosition(V4B_DEPOSIT);
                v4bR.setPosition(V4B_DEPOSIT);
                break;
        }
    }
}
