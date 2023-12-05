package org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.DepositSubsystem;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.*;

public class Diff {
    Servo diffL, diffR;
    Hardware hardware = new Hardware();

    public enum DiffState{
        PICKUP,
        DEPOSIT
    }
    DiffState diffState = DiffState.DEPOSIT;
    public Diff(HardwareMap hw){
        hardware.initDeposit(hw);
        diffL = hardware.diffL;
        diffR = hardware.diffR;

    }
    public void setDiffState(DiffState state){
        diffState = state;
        switch(state){
            case PICKUP:
                diffL.setPosition(DIFFL_PICKUP);
                diffR.setPosition(DIFFR_PICKUP);
                break;
            case DEPOSIT:
                diffL.setPosition(DIFFL_DEPOSIT);
                diffR.setPosition(DIFFR_DEPOSIT);
                break;
        }
    }
}
