package org.firstinspires.ftc.teamcode.NonOpmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;

import java.util.Vector;

public class AutoTrajectories {

    SampleMecanumDrive drive;
    public Pose2d startPose = new Pose2d(0,0,0);

    Pose2d preScore;
    Pose2d score;
    Trajectory toScore;
    Trajectory back;

    public enum AutoStartLocation{
        BLUE_LEFT,
        BLUE_RIGHT,
        RED_LEFT,
        RED_RIGHT
    }
    AutoStartLocation autoStartLocation = AutoStartLocation.BLUE_LEFT;

    public AutoTrajectories(HardwareMap hw, AutoStartLocation autoStartLocation){
        drive = new SampleMecanumDrive(hw);
        drive.setPoseEstimate(startPose);
        this.autoStartLocation = autoStartLocation;
        switch(autoStartLocation){
            case BLUE_LEFT:

                toScore = drive.trajectoryBuilder(startPose)
                        .splineToConstantHeading(new Vector2d(45,-27), Math.toRadians(0))
                        .build();
                back = drive.trajectoryBuilder(new Pose2d(0,0,0))
                        .back(1)
                        .build();

                break;



            case BLUE_RIGHT:

                break;



            case RED_LEFT:

                break;



            case RED_RIGHT:

                toScore = drive.trajectoryBuilder(startPose)
                        .splineToConstantHeading(new Vector2d(45,27), Math.toRadians(0))
                        .build();
                back = drive.trajectoryBuilder(new Pose2d(0,0,0))
                        .back(3)
                        .build();

                break;
        }
    }
}
