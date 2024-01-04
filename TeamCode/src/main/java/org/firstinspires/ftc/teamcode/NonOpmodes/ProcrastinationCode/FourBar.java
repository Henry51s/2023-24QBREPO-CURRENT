package org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_INIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_INTERMEDIATE;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_PICKUP;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;


@Config
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
    public double[] intermediatePositions;
    public static int delay = 25;
    public static int stepRatio = 150;


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
                setFourBarPositionSlow(V4B_INIT);
                break;
            case PICKUP:
                setFourBarPositionSlow(V4B_PICKUP);
                break;
            case INTERMEDIATE:
                setFourBarPositionSlow(V4B_INTERMEDIATE);
                break;
            case DEPOSIT:
                setFourBarPositionSlow(V4B_DEPOSIT);
                break;
        }
    }
    public double getPosition(){
        return v4bL.getPosition();
    }
    public FourBarState getFourBarState(){
        return fourBarState;
    }

    public void calculateIntermediatePositions(double targetPosition){
        double currentPosition = getPosition();
        double dPosition = Math.abs(targetPosition - currentPosition);
        double direction = Math.signum(targetPosition - currentPosition);

        int pathSections = Math.max((int) (dPosition*stepRatio),1);

        intermediatePositions = new double[pathSections];

        intermediatePositions[0] = currentPosition + (dPosition/pathSections)*direction;
        for(int i = 1;i < intermediatePositions.length;i++){
            intermediatePositions[i] = intermediatePositions[i-1] + (dPosition/pathSections)*direction;
        }
    }

    public void setFourBarPositionSlow(double targetPosition){
        int delayCounter = 0;

        calculateIntermediatePositions(targetPosition);
        for(int i = 0;i < intermediatePositions.length;i++){
            servoTime.reset();
            while(servoTime.milliseconds() <= delay){
                delayCounter++;
            }
            setFourBarPosition(intermediatePositions[i]);
            }
        }

    }


