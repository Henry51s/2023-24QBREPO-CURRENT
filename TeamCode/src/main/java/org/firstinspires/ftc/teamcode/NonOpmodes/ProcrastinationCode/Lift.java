package org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.LIFT_HIGH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.LIFT_LOW;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.LIFT_MED;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.LIFT_RETRACTED;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@Config
public class Lift {
    DcMotorEx lift;
    Hardware hardware = new Hardware();

    public static double power = 0.25;

    public static int targetPosition = 0;
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
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setTargetPosition(0);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(power);
    }

    public void setTargetPosition(int targetPosition){
        lift.setTargetPosition(targetPosition);
    }
    public void setLiftState(LiftState state){
        liftState = state;
        /*switch(state){
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
        }*/
    }
    public void setPower(double power){
        this.power = power;
    }
    /*public void loopLift(){
        pidf.setPIDF(p,i,d,f);
        output = pidf.calculate(
          getCurrentPosition(), targetPosition
        );
        lift.setVelocity(output);
    }*/
    public int getCurrentPosition(){
        return lift.getCurrentPosition();
    }
    public LiftState getLiftState(){
        return liftState;
    }


}
