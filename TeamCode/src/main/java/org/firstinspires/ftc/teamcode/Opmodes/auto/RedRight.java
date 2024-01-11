package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;

@Autonomous(name="RedRight")
public class RedRight extends LinearOpMode {
    AutoTrajectories autoTrajectories;
    TrajectorySequence scoreSpikeMark, scoreBackBoard, park;
    SampleMecanumDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        autoTrajectories = new AutoTrajectories(hardwareMap, AutoTrajectories.AutoLocation.RED_RIGHT);
        drive = autoTrajectories.drive;
        scoreSpikeMark = autoTrajectories.scoreSpikeMark;
        //scoreBackBoard = autoTrajectories.scoreBackBoard;
        //park = autoTrajectories.park;


        waitForStart();
        if(isStopRequested()){
            return;
        }

        drive.followTrajectorySequence(scoreSpikeMark);
        //drive.followTrajectorySequence(scoreBackBoard);
        //drive.followTrajectorySequence(park);



        while(opModeIsActive()){

        }
    }
}
