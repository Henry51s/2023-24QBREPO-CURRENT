package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.FourBarDifferentialStates;
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
    private double multipliedOffset = 0;
    private int turns = 0;
    private int maxTurns = 2;

    private FourBarDifferentialStates state = FourBarDifferentialStates.DEPOSIT;
    double[] diffPositions = new double[2];

    private Gamepad currentGamepad = new Gamepad(), previousGamepad = new Gamepad();

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

    public void setState(FourBarDifferentialStates state){
        this.state = state;
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
                diffL.setPosition(DIFFL_DEPOSIT + multipliedOffset);//+offset);
                diffR.setPosition(DIFFR_DEPOSIT - multipliedOffset);//-offset);
                break;
            case INIT:
                diffL.setPosition(DIFFL_INIT);
                diffR.setPosition(DIFFR_INIT);
                break;
        }
    }
    public void loopDifferential(Gamepad gamepad){
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad);

        turns = Math.max(-maxTurns, Math.min(turns, maxTurns));

        if(state == FourBarDifferentialStates.DEPOSIT){
            if(currentGamepad.dpad_right && !previousGamepad.dpad_right){
                turns++;
            }
            else if(currentGamepad.dpad_left && !previousGamepad.dpad_left){
                turns--;
            }
        }
        else{
            turns = 0;
        }
        multipliedOffset = offset*turns;
    }
    public double[] getDiffPositions(){
        diffPositions[0] = diffL.getPosition();
        diffPositions[1] = diffR.getPosition();
        return diffPositions;
    }
    public FourBarDifferentialStates getState(){
        return state;
    }
}
