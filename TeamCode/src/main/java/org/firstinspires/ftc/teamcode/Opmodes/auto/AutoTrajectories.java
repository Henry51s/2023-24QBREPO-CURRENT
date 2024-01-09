package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.trajectorysequence.TrajectorySequence;

public class AutoTrajectories {

    public SampleMecanumDrive drive;

    public Pose2d startPose;
    public TrajectorySequence scoreSpikeMark;
    public TrajectorySequence scoreBackBoard;
    public TrajectorySequence park;



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
        this.autoLocation = autoLocation;
        switch(autoLocation){
            case RED_RIGHT:


                break;

            case RED_LEFT: //Work on this for now
                startPose = new Pose2d(-35, -61, Math.toRadians(270));
                scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                        .setReversed(true)
                        .splineTo(new Vector2d(-30,-12), Math.toRadians(90))
                        .setReversed(false)
                        .turn(Math.toRadians(180))
                        .build();
                scoreBackBoard = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                        .waitSeconds(2)
                        .splineTo(new Vector2d(0,-14), Math.toRadians(0))
                        .splineToConstantHeading(new Vector2d(28,-14), Math.toRadians(0))
                        .splineToConstantHeading(new Vector2d(48,-35), Math.toRadians(0))
                        .build();

                park = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                        .waitSeconds(2)
                        .strafeRight(24)
                        .build();
                break;

            case BLUE_RIGHT:

                break;

            case BLUE_LEFT:


                break;
        }
        drive.setPoseEstimate(startPose);
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
