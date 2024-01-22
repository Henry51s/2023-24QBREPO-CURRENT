package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;


@Autonomous(name="RedLong")
public class RedLong extends LinearOpMode {


    AutoTrajectories autoTrajectories;
    SampleMecanumDrive drive;
    Hardware hardware = new Hardware();

    TrajectorySequence scoreSpikeMark, scoreBackDrop;
    @Override
    public void runOpMode() throws InterruptedException {
        autoTrajectories = new AutoTrajectories(hardwareMap);
        autoTrajectories.setPath(AutoTrajectories.AutoLocation.RED_LONG, AutoTrajectories.SpikeMark.RIGHT);
        drive = autoTrajectories.drive;
        scoreSpikeMark = autoTrajectories.scoreSpikeMark;
        scoreBackDrop = autoTrajectories.scoreBackDrop;

        hardware.initAuto(hardwareMap);



        waitForStart();
        if(isStopRequested()){
            return;
        }
        drive.followTrajectorySequence(scoreSpikeMark);
        drive.followTrajectorySequence(scoreBackDrop);
    }

}
