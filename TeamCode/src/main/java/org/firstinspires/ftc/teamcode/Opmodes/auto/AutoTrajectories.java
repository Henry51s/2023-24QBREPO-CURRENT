package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.trajectorysequence.TrajectorySequence;

public class AutoTrajectories {

    public SampleMecanumDrive drive;
    public Pose2d startPose = new Pose2d(0,0,0);

    Trajectory toSpikeMark;
    Trajectory toScore;
    Trajectory back;
    public TrajectorySequence liamTrajectory;


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

    public AutoTrajectories(HardwareMap hw, AutoLocation autoLocation){
        drive = new SampleMecanumDrive(hw);
        drive.setPoseEstimate(startPose);

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
                liamTrajectory = drive.trajectorySequenceBuilder(new Pose2d(-35, -61, Math.toRadians(270)))
                        .setReversed(true)
                        .splineTo(new Vector2d(-35,-12), Math.toRadians(90))
                        .setReversed(false)
                        .turn(Math.toRadians(180))
                        .back(3)
                        // deposit purple (you may have to move back slightly)
                        .waitSeconds(2)
                        .splineTo(new Vector2d(0,-14), Math.toRadians(0))
                        .splineTo(new Vector2d(28,-14), Math.toRadians(0))
                        .splineTo(new Vector2d(48,-35), Math.toRadians(0))
                        // deposit yellow
                        .waitSeconds(2)
                        // parked
                        .strafeRight(24)
                        .build();


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
