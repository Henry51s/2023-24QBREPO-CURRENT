package org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode;

import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.*;

public class Differential {
    Servo diffL, diffR;
    Hardware hardware = new Hardware();
    double offset = 0;

    public enum DiffState{
        PICKUP,
        DEPOSIT
    }
    double[] diffPositions = new double[2];
    DiffState diffState = DiffState.DEPOSIT;
    public Differential(HardwareMap hw){
        hardware.initDeposit(hw);
        diffL = hardware.diffL;
        diffR = hardware.diffR;

    }
    public void setDiffLPosition(double position){
        diffL.setPosition(position);
    }
    public void setDiffRPosition(double position){
        diffR.setPosition(position);
    }
    public double[] getDiffPositions(){
        diffPositions[0] = diffL.getPosition();
        diffPositions[1] = diffR.getPosition();
        return diffPositions;
    }
    public void setDiffState(DiffState state){
        diffState = state;
        switch(state){
            case PICKUP:
                diffL.setPosition(DIFFL_PICKUP);
                diffR.setPosition(DIFFR_PICKUP);
                break;
            case DEPOSIT:
                diffL.setPosition(DIFFL_DEPOSIT+offset);
                diffR.setPosition(DIFFR_DEPOSIT-offset);
                break;
        }
    }
    public void setOffset(double offset){
        this.offset = offset;
    }
    public DiffState getDiffState(){
        return diffState;
    }
}
