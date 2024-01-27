package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.DRONE_LATCH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.DRONE_RELEASE;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.CLIMB_LATCH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.CLIMB_RELEASE;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

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
    Servo climbL, climbR, drone;

    Hardware hardware = new Hardware();
    public void initSideQuest(HardwareMap hw){
        hardware.initClimbAndDrone(hw);
        climbL = hardware.climb1;
        climbR = hardware.climb2;
        drone = hardware.drone;
    }

    public void setClimbPosition(double position){
        climbL.setPosition(position);
        climbR.setPosition(position);
    }
    public void setDronePosition(double position){
        drone.setPosition(position);
    }

    public void latchClimb(){
        setClimbPosition(CLIMB_LATCH);

    }
    public void releaseClimb(){
        setClimbPosition(CLIMB_RELEASE);

    }
    public void latchDrone(){
        drone.setPosition(DRONE_LATCH);

    }
    public void releaseDrone(){
        drone.setPosition(DRONE_RELEASE);
    }
}
