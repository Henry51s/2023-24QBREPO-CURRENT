package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.NonOpmodes.AutoTrajectories;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;

@Autonomous(name="RedLeft")
public class RedLeft extends LinearOpMode {


    AutoTrajectories autoTrajectories;
    SampleMecanumDrive drive;
    @Override
    public void runOpMode() throws InterruptedException {
        autoTrajectories = new AutoTrajectories(hardwareMap);
        drive = autoTrajectories.drive;
        autoTrajectories.setAutoLocation(AutoTrajectories.AutoLocation.RED_LEFT);


        waitForStart();
        if(isStopRequested()){
            return;
        }
        drive.followTrajectorySequence(autoTrajectories.liamTrajectory);



    }

}
