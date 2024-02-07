package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;

@Config
public class Drive {

    private static Drive instance;
    public static Drive getInstance(){
        if(instance == null){
            instance = new Drive();
        }
        return instance;
    }
    private DcMotor frontLeft, frontRight, backLeft, backRight;

    private Hardware hardware = new Hardware();

    private int flipMultiplier = 1;
    public static double frontStrafeMultiplier = 0.7;
    public static double backStrafeMultiplier = 1;
    public static double driveMultiplier = 0.8;


    public enum DriveState{
        NORMAL,
        REVERSED
    }
    private DriveState driveState = DriveState.NORMAL;


    public void initDrive(HardwareMap hw){
        hardware.initDrive(hw);
        frontLeft = hardware.frontLeft;
        frontRight = hardware.frontRight;
        backLeft = hardware.backLeft;
        backRight = hardware.backRight;
    }

    public void setDriveState(DriveState driveState){
        this.driveState = driveState;
        switch(driveState){
            case NORMAL:
                flipMultiplier = 1;
                break;
            case REVERSED:
                flipMultiplier = -1;
                break;
        }
    }
    public void loopDrive(Gamepad gamepad){

        double y = -gamepad.left_stick_y * flipMultiplier * driveMultiplier; // Remember, Y stick is reversed!
        double x = gamepad.left_stick_x * flipMultiplier * driveMultiplier;
        double rx = gamepad.right_stick_x*0.75 * driveMultiplier;



        frontLeft.setPower(y + x* frontStrafeMultiplier + rx);
        backLeft.setPower(y - x*backStrafeMultiplier + rx);
        frontRight.setPower(y - x* frontStrafeMultiplier - rx);
        backRight.setPower(y + x*backStrafeMultiplier - rx);

    }
}
