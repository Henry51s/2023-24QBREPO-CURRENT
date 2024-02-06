package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms;


import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.EXTENDO_CLIMB;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.EXTENDO_FAR;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.EXTENDO_MAX_POWER;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.EXTENDO_MED;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.EXTENDO_RETRACTED;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.EXTENDO_SHORT;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;

@Config
public class Extension {
    //Max position: 999
    private static Extension instance;
    public static Extension getInstance(){
        if(instance == null){
            instance = new Extension();
        }
        return instance;
    }

    public static double p = 0;
    public static double i = 0;
    public static double d = 0;
    public static double positionTolerance = 0;
    public static double joystickMultiplier = 5;
    PIDController pid = new PIDController(p,i,d);







    private int targetPosition = 0;
    private double currentThreshold = 0;
    private int homePosition = 0;
    private DcMotorEx extendoL, extendoR;
    private Hardware hardware = new Hardware();

    public enum ExtensionState{
        RETRACTED,
        SHORT,
        MED,
        FAR,
        CLIMB
    }
    ExtensionState extensionState = ExtensionState.RETRACTED;

    public void initExtension(HardwareMap hw){

        hardware.initExtension(hw);
        extendoL = hardware.extendoL;
        extendoR = hardware.extendoR;

        /*extendoL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendoR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setTargetPosition(0);
        extendoL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendoR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(EXTENDO_MAX_POWER);*/

        extendoL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extendoR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pid.setTolerance(positionTolerance);

        targetPosition = 0;
    }
    public void initExtension(HardwareMap hw, boolean debug){
        hardware.initExtension(hw);
        extendoL = hardware.extendoL;
        extendoR = hardware.extendoR;
    }

    private double calculateVel(){
        double vel = pid.calculate(
                getAveragePosition(), targetPosition
        );
        return vel;
    }

    private void setVelocity(double velocity){
        extendoL.setVelocity(velocity);
        extendoR.setVelocity(velocity);
    }


    public void setPower(double power){
        extendoL.setPower(power);
        extendoR.setPower(power);
    }
    public void setTargetPosition(int targetPosition){
        extendoL.setTargetPosition(targetPosition);
        extendoR.setTargetPosition(targetPosition);
    }
    public void setExtensionState(ExtensionState extensionState){
        this.extensionState = extensionState;
        switch(extensionState){
            case RETRACTED:
                setTargetPosition(EXTENDO_RETRACTED);
                break;
            case SHORT:
                setTargetPosition(EXTENDO_SHORT);
                break;
            case MED:
                setTargetPosition(EXTENDO_MED);
                break;
            case FAR:
                setTargetPosition(EXTENDO_FAR);
                break;
            case CLIMB:
                setTargetPosition(EXTENDO_CLIMB);
                break;
        }
    }

    Gamepad current = new Gamepad(), previous = new Gamepad();


    public void loopExtension(Gamepad gamepad){



        previous.copy(current);
        current.copy(gamepad);
        targetPosition += -gamepad.left_stick_y*joystickMultiplier;
        targetPosition = Math.max(0, Math.min(targetPosition, homePosition + EXTENDO_FAR));

        if(current.left_stick_button && !previous.left_stick_button){
            targetPosition = homePosition;
        }

        setTargetPosition(targetPosition);
        double output = calculateVel();
        setVelocity(output);

        if(getAverageCurrent(CurrentUnit.AMPS) > currentThreshold && calculateVel() < 0){
            setVelocity(0);
            homePosition = getAveragePosition();
            targetPosition = homePosition;

        }

    }
    public int getMotorLCurrentPosition(){return extendoL.getCurrentPosition();}
    public int getMotorRCurrentPosition(){
        return extendoR.getCurrentPosition();
    }
    public int getAveragePosition(){
        return (getMotorLCurrentPosition() + getMotorRCurrentPosition())/2;
    }
    public double getAverageCurrent(CurrentUnit currentUnit){
        return (extendoL.getCurrent(currentUnit) + extendoR.getCurrent(currentUnit))/2;
    }

    public int getTargetPosition(){
        return targetPosition;
    }

}
