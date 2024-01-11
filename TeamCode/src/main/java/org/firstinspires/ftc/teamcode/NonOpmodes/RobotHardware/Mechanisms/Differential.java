package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.*;

public class Differential {
    private static Differential instance;
    public static Differential getInstance(){
        if(instance == null){
            instance = new Differential();
        }
        return instance;
    }
    private Servo diffL, diffR;
    private Hardware hardware = new Hardware();
    private double offset = 0.135;
    private int turns = 0;
    private int maxTurns = 2;

    public enum DiffState{
        PICKUP,
        INTERMEDIATE,
        DEPOSIT
    }
    DiffState diffState = DiffState.DEPOSIT;
    double[] diffPositions = new double[2];

    Gamepad currentGamepad = new Gamepad(), previousGamepad = new Gamepad();
    /*public Differential(HardwareMap hw){
        hardware.initDifferential(hw);
        diffL = hardware.diffL;
        diffR = hardware.diffR;
    }*/

    public void initDifferential(HardwareMap hw){
        hardware.initDifferential(hw);
        diffL = hardware.diffL;
        diffR = hardware.diffR;
    }
    public void setDiffLPosition(double position){
        diffL.setPosition(position);
    }
    public void setDiffRPosition(double position){
        diffR.setPosition(position);
    }

    public void setDiffState(DiffState state){
        diffState = state;
        switch(state){
            case PICKUP:
                diffL.setPosition(DIFFL_PICKUP);
                diffR.setPosition(DIFFR_PICKUP);
                break;
            case INTERMEDIATE:
                diffL.setPosition(DIFFL_INTERMEDIATE);
                diffR.setPosition(DIFFR_INTERMEDIATE);
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
    public double[] getDiffPositions(){
        diffPositions[0] = diffL.getPosition();
        diffPositions[1] = diffR.getPosition();
        return diffPositions;
    }
    public void loopDifferential(Gamepad gamepad){
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad);
        if(diffState == DiffState.DEPOSIT){
            if(currentGamepad.dpad_left && !previousGamepad.dpad_left){
                turns++;
            }
            else if (currentGamepad.dpad_right && !previousGamepad.dpad_right){
                turns--;
            }
            if(Math.abs(turns) >= maxTurns){
                turns = (int) Math.signum(turns)*maxTurns;
            }
        }
        else
            turns = 0;
    }
    public DiffState getDiffState(){
        return diffState;
    }
}
