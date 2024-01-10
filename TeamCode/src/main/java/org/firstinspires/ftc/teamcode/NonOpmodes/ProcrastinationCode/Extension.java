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
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@Config
public class Extension {


    public static int targetPosition = 0;
    public static double kp = 0;
    public static double ki = 0;
    public static double kd = 0;
    public static double velFactor = 0.5;
    PIDController pid = new PIDController(kp,ki,kd);

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
        hardware.initExtension(hw);
        extendoL = hardware.extendoL;
        extendoR = hardware.extendoR;
        pid.setTolerance(TOLERANCE);
        
    }
    public void setPower(double power){
        extendoL.setPower(power);
        extendoR.setPower(power);
    }
    private void setVelocity(double velocity){
        extendoL.setVelocity(velocity);
        extendoR.setVelocity(velocity);
    }

    public void setTargetPosition(int targetPosition){
        this.targetPosition = targetPosition;
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
    public void loopExtension(){
        double output = pid.calculate(
                extendoL.getCurrentPosition(), targetPosition
        ); //Getting position from extendoL
        setVelocity(output*velFactor);
    }
    public int getCurrentPosition(){return extendoL.getCurrentPosition();}

}
