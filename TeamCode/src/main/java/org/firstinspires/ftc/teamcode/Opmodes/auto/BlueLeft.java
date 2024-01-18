package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.FourBar;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;

@Autonomous(name="BlueLeft")
public class BlueLeft extends LinearOpMode {

    Hardware hardware = new Hardware();
    Intake intake;


    AutoTrajectories autoTrajectories;
    SampleMecanumDrive drive;
    TrajectorySequence scoreSpikeMark, scoreBackBoard, park;


    @Override
    public void runOpMode() throws InterruptedException {
        autoTrajectories = new AutoTrajectories(hardwareMap, AutoTrajectories.AutoLocation.BLUE_LEFT);
        drive = autoTrajectories.drive;

        scoreSpikeMark = autoTrajectories.scoreSpikeMark;
        scoreBackBoard = autoTrajectories.scoreBackBoard;
        park = autoTrajectories.park;

        waitForStart();
        if(isStopRequested()){
            return;
        }
        drive.followTrajectorySequence(scoreSpikeMark);
        intake.runIntakeSetTime(500, true);
        drive.followTrajectorySequence(scoreBackBoard);







        while(opModeIsActive()){


        }


    }


}
