package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

public class BlueTopleft extends LinearOpMode {

    Hardware hardware = new Hardware();

    @Override
    public void runOpMode() throws InterruptedException {

        while(opModeIsActive()){
            hardware.loopRobot();
        }
    }


}
