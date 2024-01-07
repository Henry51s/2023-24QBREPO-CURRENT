package org.firstinspires.ftc.teamcode.NonOpmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;

public class AutoTrajectories {

    SampleMecanumDrive drive;
    public Pose2d startPose = new Pose2d(0,0,0);

    Trajectory toSpikeMark;
    Trajectory toScore;
    Trajectory back;


    public enum AutoLocation {
        BLUE_LEFT,
        BLUE_RIGHT,
        RED_LEFT,
        RED_RIGHT
    }

    public enum SpikeMark {
        LEFT,
        MIDDLE,
        RIGHT
    }
    SpikeMark spikeMark = SpikeMark.LEFT;
    AutoLocation autoLocation = AutoLocation.BLUE_LEFT;

    public AutoTrajectories(HardwareMap hw){
        drive = new SampleMecanumDrive(hw);
        drive.setPoseEstimate(startPose);
    }

    public void setAutoLocation(AutoLocation autoLocation){
        this.autoLocation = autoLocation;
        switch(autoLocation){
            case RED_RIGHT:
                toScore = drive.trajectoryBuilder(startPose)
                        .splineToConstantHeading(new Vector2d(45,27), Math.toRadians(0))
                        .build();
                back = drive.trajectoryBuilder(new Pose2d(0,0,0))
                        .back(3)
                        .build();


                break;

            case RED_LEFT:

                break;

            case BLUE_RIGHT:

                break;

            case BLUE_LEFT:
                toScore = drive.trajectoryBuilder(startPose)
                        .splineToConstantHeading(new Vector2d(45,-27), Math.toRadians(0))
                        .build();
                back = drive.trajectoryBuilder(new Pose2d(0,0,0))
                        .back(1)
                        .build();

                break;
        }
    }

    public void setSpikeMark(SpikeMark spikeMark){
        this.spikeMark = spikeMark;
        switch(spikeMark){
            case LEFT:
                //Define toSpikeMark trajectories
                break;
            case RIGHT:

                break;
            case MIDDLE:

                break;
        }
    }
}
