package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;

@Autonomous(name="RedLeft")
public class RedLeft extends LinearOpMode {


    AutoTrajectories autoTrajectories;
    SampleMecanumDrive drive;
    @Override
    public void runOpMode() throws InterruptedException {
        autoTrajectories = new AutoTrajectories(hardwareMap, AutoTrajectories.AutoLocation.RED_LEFT);
        drive = autoTrajectories.drive;



        waitForStart();
        if(isStopRequested()){
            return;
        }
        drive.followTrajectorySequence(autoTrajectories.scoreSpikeMark);
        drive.followTrajectorySequence(autoTrajectories.scoreBackBoard);
        drive.followTrajectorySequence(autoTrajectories.park);
    }

}
