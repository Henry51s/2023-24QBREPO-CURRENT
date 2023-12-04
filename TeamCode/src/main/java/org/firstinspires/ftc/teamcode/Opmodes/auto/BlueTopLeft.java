package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

import java.util.TreeMap;

@Autonomous(name="BlueTopleft")
public class BlueTopLeft extends LinearOpMode {

    MecanumDrive md = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
    TrajectoryActionBuilder trajectoryActionBuilder = md.actionBuilder(new Pose2d(0,0,0))
            .lineToX(10)
            .endTrajectory();
    SequentialAction action = new SequentialAction();
    @Override
    public void runOpMode() throws InterruptedException {
    }

}
