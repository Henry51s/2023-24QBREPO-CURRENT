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
    private int flipMultiplier = 1;
    public static double driveMultiplier = 1;
    public static double turnMultiplier = 0.75;

    private double x= 0;
    private double y= 0;
    private double rx = 0;
    private double frontLeftPower = 0, frontRightPower = 0, backLeftPower = 0, backRightPower = 0;


    public enum DriveState{
        NORMAL,
        REVERSED
    }
    private DriveState driveState = DriveState.NORMAL;


    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private Hardware hardware = new Hardware();


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

        y = -gamepad.left_stick_y * flipMultiplier * driveMultiplier; // Remember, Y stick is reversed!
        x = gamepad.left_stick_x * flipMultiplier * driveMultiplier;
        rx = gamepad.right_stick_x*turnMultiplier * driveMultiplier;

        frontLeftPower = y + x+ rx;
        frontRightPower = y -x + rx;
        backLeftPower = y - x - rx;
        backRightPower = y + x -rx;



        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(frontRightPower);
        frontRight.setPower(backLeftPower);
        backRight.setPower(backRightPower);

    }
    public double[] getJoystickInputs() {
        double[] inputs = {x,y,rx};
        return inputs;
    }
    public double[] getMotorPowers(){
        double[] motorPowers = {frontLeftPower, frontRightPower, backLeftPower, backRightPower};
        return motorPowers;
    }
}
