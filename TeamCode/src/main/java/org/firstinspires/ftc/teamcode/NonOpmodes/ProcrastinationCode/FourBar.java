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

    static int callCounter = 0;
    ElapsedTime servoTime = new ElapsedTime();


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

    public double[] setFourBarPositionSlow(double targetPosition, int pathSections){
        double currentPosition = getPosition();
        double dPosition = Math.abs(targetPosition - currentPosition);
        double direction = Math.signum(targetPosition - currentPosition);
        int delay = 100;
        double[] intermediatePositions = new double[pathSections + 1];

        intermediatePositions[0] = currentPosition + (dPosition/pathSections)*direction;

        for(int i = 1;i < intermediatePositions.length;i++){
            intermediatePositions[i] = intermediatePositions[i-1] + (dPosition/pathSections)*direction;
        }
        /*for(int j = 0;j < intermediatePositions.length;j++){
            servoTime.reset();
            if(servoTime.milliseconds() > 100){
                setFourBarPosition(intermediatePositions[j]);
            }
        }
        */
        return intermediatePositions;
    }
}

