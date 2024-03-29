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
    public enum State{
        INIT,
        DEPOSIT,
        DEPOSIT_VERTICAL,
        PICKUP,
        INTERMEDIATE_PTD,
        TILT_LEFT,
        TILT_RIGHT
    }
    private State state = State.INIT;
    private Servo diffL, diffR;
    private Hardware hardware = new Hardware();
    private double offset = 0.166;
    private double multipliedOffset = 0;
    private int turns = 0;
    private int maxTurns = 1;


    private double[] diffPositions = new double[2];

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

    public void setState(State state){
        this.state = state;
        switch(state){
            case PICKUP:
                diffL.setPosition(DIFFL_PICKUP);
                diffR.setPosition(DIFFR_PICKUP);
                break;
            case INTERMEDIATE_PTD:
                diffL.setPosition(DIFFL_INTERMEDIATE_PTD);
                diffR.setPosition(DIFFR_INTERMEDIATE_PTD);
                break;
            case DEPOSIT:
                diffL.setPosition(DIFFL_DEPOSIT + (offset * turns));//+offset);
                diffR.setPosition(DIFFR_DEPOSIT - (offset * turns));//-offset);
                break;
            case INIT:
                diffL.setPosition(DIFFL_INIT);
                diffR.setPosition(DIFFR_INIT);
                break;
            case TILT_LEFT:
                diffL.setPosition(DIFFL_DEPOSIT_45_L);
                diffR.setPosition(DIFFR_DEPOSIT_45_L);
                break;
            case TILT_RIGHT:
                diffL.setPosition(DIFFL_DEPOSIT_45_R);
                diffR.setPosition(DIFFR_DEPOSIT_45_R);
                break;
            case DEPOSIT_VERTICAL:
                diffL.setPosition(DIFFL_DEPOSIT_90);
                diffR.setPosition(DIFFR_DEPOSIT_90);
                break;
        }
    }
    public void loopDifferential(Gamepad gamepad){
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad);

        turns = Math.max(-maxTurns, Math.min(turns, maxTurns));


        if(state == State.DEPOSIT){
            if(currentGamepad.dpad_right && !previousGamepad.dpad_right){
                turns++;
                setState(State.DEPOSIT);
            }
            if(currentGamepad.dpad_left && !previousGamepad.dpad_left){
                turns--;
                setState(State.DEPOSIT);
            }
        }
        multipliedOffset = offset*turns;
    }
    public double[] getDiffPositions(){
        diffPositions[0] = diffL.getPosition();
        diffPositions[1] = diffR.getPosition();
        return diffPositions;
    }
    public State getState(){
        return state;
    }
}
