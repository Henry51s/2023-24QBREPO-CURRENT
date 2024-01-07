package org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.LIFT_HIGH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.LIFT_LOW;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.LIFT_MED;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.LIFT_RETRACTED;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@Config
public class Lift {
    DcMotorEx lift;
    Hardware hardware = new Hardware();

    public static double p = 0;
    public static double i = 0;
    public static double d = 0;
    public static double f = 0;

    public static int tolerance = 1;
    public static int targetPosition = 0;
    PIDFController pidf = new PIDFController(p,i,d,f);
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
        pidf.setTolerance(tolerance);
    }

    public void setTargetPosition(int targetPosition){
        this.targetPosition = targetPosition;
    }
    public void setLiftState(LiftState state){
        liftState = state;
        switch(state){
            case RETRACTED:
                setTargetPosition(LIFT_RETRACTED);
                break;
            case LOW:
                setTargetPosition(LIFT_LOW);
                break;
            case MED:
                setTargetPosition(LIFT_MED);
                break;
            case HIGH:
                setTargetPosition(LIFT_HIGH);
                break;
        }
    }
    public void setPower(double power){
        lift.setPower(power);
    }
    public void loopLift(){
        double output = pidf.calculate(
          getCurrentPosition(), targetPosition
        );
        lift.setVelocity(output);
    }
    public int getCurrentPosition(){
        return lift.getCurrentPosition();
    }
    public LiftState getLiftState(){
        return liftState;
    }


}
