package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.LIFT_AUTO_LOW;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.LIFT_HIGH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.LIFT_LOW;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.LIFT_MAX_POWER;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.LIFT_MED;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.LIFT_RETRACTED;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;

@Config
public class Lift {
    private static Lift instance;
    public static Lift getInstance(){
        if(instance == null){
            instance = new Lift();
        }
        return instance;
    }
    private DcMotorEx lift;
    private Hardware hardware = new Hardware();



    private int targetPosition = 0;
    public enum LiftState{
        RETRACTED,
        LOW,
        MED,
        HIGH,
        AUTO_LOW,
    }
    private LiftState liftState = LiftState.RETRACTED;


    public void initLift(HardwareMap hw){
        hardware.initLift(hw);
        lift = hardware.lift;
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setTargetPosition(0);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(LIFT_MAX_POWER);


    }
    public void initLift(HardwareMap hw, boolean debug){
        hardware.initLift(hw);
        lift = hardware.lift;

        if(!debug){
            lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            lift.setTargetPosition(0);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(LIFT_MAX_POWER);
        }


    }

    public void setTargetPosition(int targetPosition){
        lift.setTargetPosition(targetPosition);
    }
    public void setLiftState(LiftState state){
        liftState = state;
        switch(state){
            case RETRACTED:
                targetPosition = LIFT_RETRACTED;
                setTargetPosition(LIFT_RETRACTED);
                break;
            case LOW:
                targetPosition = LIFT_LOW;
                setTargetPosition(LIFT_LOW);
                break;
            case MED:
                targetPosition = LIFT_MED;
                setTargetPosition(LIFT_MED);
                break;
            case HIGH:
                targetPosition = LIFT_HIGH;
                setTargetPosition(LIFT_HIGH);
                break;
            case AUTO_LOW:
                targetPosition = LIFT_AUTO_LOW;
                setTargetPosition(LIFT_AUTO_LOW);
                break;
        }
    }

    public int getTargetPosition(){
        return targetPosition;
    }
    public int getCurrentPosition(){
        return lift.getCurrentPosition();
    }
    public LiftState getLiftState(){
        return liftState;
    }


}
