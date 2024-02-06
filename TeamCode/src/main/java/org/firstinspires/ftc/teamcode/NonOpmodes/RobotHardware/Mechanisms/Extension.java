package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms;


import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.EXTENDO_CLIMB;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.EXTENDO_FAR;
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

    public enum Mode{
        HOMING,
        OPERATIONAL
    }
    Mode mode = Mode.OPERATIONAL;

    public static double p = 15;
    public static double i = 0;
    public static double d = 0;
    public static double positionTolerance = 1;
    public static double joystickMultiplier = 15;
    private PIDController pid;







    private int targetPosition = 0;
    public static double currentThreshold = 3;
    public int homePosition = 0;
    public static double homingVelocity = 500;
    public static double maxVelocity = 900;
    public static int homePositionBuffer = 3;
    double currentMaxVelocity = 0;
    public double outputL = 0;
    public double outputR = 0;
    public double limitedOutputL = 0;
    public double limitedOutputR = 0;
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
        pid = new PIDController(p,i,d);

        hardware.initExtension(hw);
        extendoL = hardware.extendoL;
        extendoR = hardware.extendoR;


        extendoL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendoR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extendoL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extendoR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pid.setTolerance(positionTolerance);

        targetPosition = 0;
        homePosition = 0;
    }
    public void initExtension(HardwareMap hw, boolean debug){
        hardware.initExtension(hw);
        extendoL = hardware.extendoL;
        extendoR = hardware.extendoR;
    }

    private double calculateVel(int currentPosition){
        double vel = pid.calculate(
                currentPosition, targetPosition
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

    public double limitSpeed(double velocity, double maxVelocity){
        return Math.max(-maxVelocity, Math.min(velocity, maxVelocity));
    }
    public void home(){
        mode = Mode.HOMING;
        homePosition = -999999;
        setTargetPosition(homePosition);

    }


    public void loopExtension(Gamepad gamepad){

        switch(mode){

            case OPERATIONAL:
                currentMaxVelocity = maxVelocity;
                break;
            case HOMING:
                currentMaxVelocity = homingVelocity;
                break;
        }

        previous.copy(current);
        current.copy(gamepad);
        targetPosition += -gamepad.left_stick_y*joystickMultiplier;
        targetPosition = Math.max(homePosition, Math.min(targetPosition, homePosition + EXTENDO_FAR));

        if(current.left_stick_button && !previous.left_stick_button){
            targetPosition = homePosition;
        }
        if(current.right_stick_button && !previous.right_stick_button){
            home();
        }

        setTargetPosition(targetPosition);
        outputL = calculateVel(extendoL.getCurrentPosition());
        outputR = calculateVel(extendoR.getCurrentPosition());

        limitedOutputL = limitSpeed(outputL, currentMaxVelocity);
        limitedOutputR = limitSpeed(outputR, currentMaxVelocity);
        extendoL.setVelocity(limitedOutputL);
        extendoR.setVelocity(limitedOutputR);

        if(getAverageCurrent(CurrentUnit.AMPS) > currentThreshold && calculateVel(extendoL.getCurrentPosition()) < 0 && mode == Mode.HOMING){
            setVelocity(0);
            homePosition = getAveragePosition();
            targetPosition = homePosition + homePositionBuffer;
            mode = Mode.OPERATIONAL;

        }

    }
    public void loopExtensionAuto(){
        outputL = calculateVel(extendoL.getCurrentPosition());
        outputR = calculateVel(extendoR.getCurrentPosition());

        limitedOutputL = limitSpeed(outputL, maxVelocity);
        limitedOutputR = limitSpeed(outputR, maxVelocity);
        extendoL.setVelocity(limitedOutputL);
        extendoR.setVelocity(limitedOutputR);

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
