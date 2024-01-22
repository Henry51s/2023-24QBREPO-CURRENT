package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;

@Autonomous(name="BlueLong")
public class BlueLong extends LinearOpMode {

    AutoTrajectories autoTrajectories;
    SampleMecanumDrive drive;
    Hardware hardware = new Hardware();

    @Override
    public void runOpMode() throws InterruptedException {

        autoTrajectories = new AutoTrajectories(hardwareMap);
        drive = autoTrajectories.drive;

        autoTrajectories.setPath(AutoTrajectories.AutoLocation.BLUE_LONG, AutoTrajectories.SpikeMark.RIGHT);

        hardware.initAuto(hardwareMap);

        waitForStart();
        if(isStopRequested()){
            return;
        }
        drive.followTrajectorySequence(autoTrajectories.scoreSpikeMark);
        drive.followTrajectorySequence(autoTrajectories.scoreBackDrop);

    }
}
