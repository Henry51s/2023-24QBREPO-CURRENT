package org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode;


import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.EXTENDO_FAR;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.EXTENDO_MED;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.EXTENDO_RETRACTED;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.EXTENDO_SHORT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.TOLERANCE;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.d;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.i;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.p;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@Config
public class Extension {
    //Max position: 999

    public static int targetPosition = 0;
    public static double power = 0.25;

    DcMotorEx extendoL, extendoR;
    Hardware hardware = new Hardware();

    public enum ExtensionState{
        RETRACTED,
        SHORT,
        MED,
        FAR
    }
    ExtensionState extensionState = ExtensionState.RETRACTED;

    public Extension(HardwareMap hw){
        hardware.initIntake(hw);
        extendoL = hardware.extendoL;
        extendoR = hardware.extendoR;
        extendoL.setDirection(DcMotorSimple.Direction.REVERSE);
        /*extendoL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendoR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setTargetPosition(0);
        extendoL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendoR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(power);*/


        
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
        }
    }
    public int getCurrentPosition(){return extendoL.getCurrentPosition();}

}
