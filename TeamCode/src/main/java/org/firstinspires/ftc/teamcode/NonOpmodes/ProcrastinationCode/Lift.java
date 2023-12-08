package org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.LIFT_HIGH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.LIFT_LOW;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.LIFT_MED;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.LIFT_RETRACTED;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.PIDMotor;
public class Lift {
    PIDMotor lift;
    Hardware hardware = new Hardware();

    double p = 0;
    double i = 0;
    double d = 0;

    public enum LiftState{
        RETRACTED,
        LOW,
        MED,
        HIGH
    }
    LiftState liftState = LiftState.RETRACTED;
    public Lift(HardwareMap hw){
        hardware.initDeposit(hw);
        lift = hardware.lift;
        lift.setCoefficients(p,i,d,0);
    }

    public void setCoefficients(double p, double i, double d){
        this.p = p;
        this.i = i;
        this.d = d;
    }

    public void setLiftState(LiftState state){
        liftState = state;
        switch(state){
            case RETRACTED:
                lift.setTargetPosition(LIFT_RETRACTED);
                break;
            case LOW:
                lift.setTargetPosition(LIFT_LOW);
                break;
            case MED:
                lift.setTargetPosition(LIFT_MED);
                break;
            case HIGH:
                lift.setTargetPosition(LIFT_HIGH);
                break;
        }
    }
}
