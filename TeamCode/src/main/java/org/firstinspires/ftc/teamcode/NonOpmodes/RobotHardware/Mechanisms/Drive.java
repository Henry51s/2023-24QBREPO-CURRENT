package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;

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

    public enum DriveState{
        NORMAL,
        REVERSED
    }
    DriveState driveState = DriveState.NORMAL;

    /*public Drive(HardwareMap hw){
        hardware.initDrive(hw);
        frontLeft = hardware.frontLeft;
        frontRight = hardware.frontRight;
        backLeft = hardware.backLeft;
        backRight = hardware.backRight;
    }*/

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

        double y = -gamepad.left_stick_y * flipMultiplier; // Remember, Y stick is reversed!
        double x = gamepad.left_stick_x * flipMultiplier;
        double rx = -gamepad.right_stick_x*0.5;

        frontLeft.setPower(y - x + rx);
        backLeft.setPower(y + x + rx);
        frontRight.setPower(y - x - rx);
        backRight.setPower(y + x - rx);
    }
}
