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
    int timeBuffer = 2000;

    public enum V4bState{
        INIT,
        PICKUP,
        DEPOSIT
    }
    V4bState v4bState = V4bState.INIT;
    ElapsedTime time = new ElapsedTime();
    public boolean beforeBuffer = true;

    public FourBar(HardwareMap hw){
        hardware.initDeposit(hw);
        v4bL = hardware.v4bL;
        v4bR = hardware.v4bR;
    }

    public double getTime(){
        return time.milliseconds();
    }
    public void setV4bState(V4bState state){ //v4b.setPosition(...)
        time.reset();
        v4bState = state;
        switch (state){
            case INIT:
                v4bL.setPosition(V4B_INIT);
                v4bR.setPosition(V4B_INIT);

                break;
            case PICKUP:
                v4bL.setPosition(V4B_PICKUP-0.2);
                v4bR.setPosition(V4B_PICKUP-0.2);
                break;

            case DEPOSIT:
                v4bL.setPosition(V4B_DEPOSIT);
                v4bR.setPosition(V4B_DEPOSIT);
                break;
        }
    }
    public void loopFourBar(){
        if(time.milliseconds() > timeBuffer && v4bState == V4bState.PICKUP){
            v4bL.setPosition(V4B_PICKUP);
            v4bR.setPosition(V4B_PICKUP);
        }

        }
    }

