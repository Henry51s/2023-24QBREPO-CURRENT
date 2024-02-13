package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.CLIMBL_LATCH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.CLIMBR_RELEASE;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.DRONE_LATCH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.DRONE_RELEASE;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.CLIMBR_LATCH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.CLIMBL_RELEASE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;

public class SideObjective {

    private static SideObjective instance;
    public static SideObjective getInstance(){
        if(instance == null){
            instance = new SideObjective();
        }
        return instance;

    }
    private SideObjective(){
    }
    private Servo drone;
    private CRServo climbL, climbR;
    private ElapsedTime timer = new ElapsedTime();

    private int winchReleaseTime = 1000;
    private int winchWindTime = 1000;
    private Hardware hardware = new Hardware();
    public void initSideQuest(HardwareMap hw){
        hardware.initClimbAndDrone(hw);
        climbL = hardware.climbL;
        climbR = hardware.climbR;
        drone = hardware.drone;
    }


    public void setDronePosition(double position){
        drone.setPosition(position);
    }

    public void setClimbServoPower(double power){
        climbL.setPower(power);
        climbR.setPower(power);
    }

    public void releaseClimbWinch(){
        setClimbServoPower(1);

    }
    public void windClimbWinch(){
        setClimbServoPower(-1);
    }
    public void latchDrone(){
        drone.setPosition(DRONE_LATCH);

    }
    public void releaseDrone(){
        drone.setPosition(DRONE_RELEASE);
    }
}
