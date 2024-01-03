package org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_INIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_INTERMEDIATE;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_PICKUP;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

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


    ElapsedTime servoTime = new ElapsedTime();
    double[] intermediatePositions;


    public FourBar(HardwareMap hw){
        hardware.initDeposit(hw);
        v4bL = hardware.v4bL;
        v4bR = hardware.v4bR;
    }
    public void setFourBarPosition(double position){
        v4bL.setPosition(position);
        v4bR.setPosition(position);
    }
    public void setFourBarState(FourBarState state){
        fourBarState = state;
        switch (state){
            case INIT:
                setFourBarPosition(V4B_INIT);
                break;
            case PICKUP:
                setFourBarPosition(V4B_PICKUP);
                break;
            case INTERMEDIATE:
                setFourBarPosition(V4B_INTERMEDIATE);
                break;
            case DEPOSIT:
                setFourBarPosition(V4B_DEPOSIT);
                break;
        }
    }
    public double getPosition(){
        return v4bL.getPosition();
    }
    public FourBarState getFourBarState(){
        return fourBarState;
    }

    public void calculateIntermediatePositions(double targetPosition, int pathSections){
        intermediatePositions = new double[pathSections];

        double currentPosition = getPosition();
        double dPosition = Math.abs(targetPosition - currentPosition);
        double direction = Math.signum(targetPosition - currentPosition);

        intermediatePositions[0] = currentPosition + (dPosition/pathSections)*direction;
        for(int i = 1;i < intermediatePositions.length;i++){
            intermediatePositions[i] = intermediatePositions[i-1] + (dPosition/pathSections)*direction;
        }
    }

    public void setFourBarPositionSlow(double targetPosition, int pathSections){
        int delayCounter = 0;
        calculateIntermediatePositions(targetPosition, pathSections);
        for(int i = 0;i < intermediatePositions.length;i++){
            servoTime.reset();
            while(servoTime.milliseconds() <= 1000){
                delayCounter++;
            }
            setFourBarPosition(intermediatePositions[i]);
            }
        }

    }


