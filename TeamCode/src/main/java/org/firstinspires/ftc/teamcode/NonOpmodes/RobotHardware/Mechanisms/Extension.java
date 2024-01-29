package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms;


import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.EXTENDO_FAR;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.EXTENDO_MAX_POWER;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.EXTENDO_MED;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.EXTENDO_RETRACTED;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.EXTENDO_SHORT;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

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
    public static int targetPosition = 0;

    private DcMotorEx extendoL, extendoR;
    private Hardware hardware = new Hardware();

    public enum ExtensionState{
        RETRACTED,
        SHORT,
        MED,
        FAR
    }
    ExtensionState extensionState = ExtensionState.RETRACTED;

    /*public Extension(HardwareMap hw){
        hardware.initExtension(hw);
        extendoL = hardware.extendoL;
        extendoR = hardware.extendoR;

        extendoL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendoR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setTargetPosition(0);
        extendoL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendoR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(power);
    }*/

    public void initExtension(HardwareMap hw){

        hardware.initExtension(hw);
        extendoL = hardware.extendoL;
        extendoR = hardware.extendoR;

        extendoL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendoR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setTargetPosition(0);
        extendoL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendoR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(EXTENDO_MAX_POWER);
    }
    public void initExtension(HardwareMap hw, boolean debug){

        hardware.initExtension(hw);
        extendoL = hardware.extendoL;
        extendoR = hardware.extendoR;
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
        }
    }

    Gamepad current = new Gamepad(), previous = new Gamepad();
    int counter = 0;

    public void loopExtension(Gamepad gamepad){
        previous.copy(current);
        current.copy(gamepad);

        if(current.dpad_right && !previous.dpad_right){
            counter++;
        }
        if(current.dpad_left && !previous.dpad_left){
            counter--;
        }

        if(counter > 3){
            counter = 3;
        }
        else if(counter < 0){
            counter = 0;
        }

        if(counter == 0){
            setExtensionState(ExtensionState.RETRACTED);
        }
        else if (counter == 1) {
            setExtensionState(ExtensionState.SHORT);
        }
        else if(counter == 2){
            setExtensionState(ExtensionState.MED);
        }
        else if(counter == 3){
            setExtensionState(ExtensionState.FAR);
        }
    }
    public int getCurrentPosition(){return extendoL.getCurrentPosition();}

}
