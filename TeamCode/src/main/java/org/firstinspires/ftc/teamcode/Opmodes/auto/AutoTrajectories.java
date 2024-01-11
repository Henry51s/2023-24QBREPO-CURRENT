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
                startPose = new Pose2d(11, -61, Math.toRadians(90));
                scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                        .splineTo(new Vector2d(11,-38), Math.toRadians(90))
                        .build();
                scoreBackBoard = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                        .setReversed(true)
                        .waitSeconds(2)
                        .turn(Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(48,-35), Math.toRadians(0))
                        .build();
                park = drive.trajectorySequenceBuilder(scoreBackBoard.end())
                        .waitSeconds(2)
                        .strafeLeft(24)
                        .build();
                break;

            case RED_LEFT: //Work on this for now
                startPose = new Pose2d(-35, -61, Math.toRadians(90));
                scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                        .splineTo(new Vector2d(-35,-12), Math.toRadians(90))
                        .build();
                scoreBackBoard = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                        .turn(Math.toRadians(180))
                        .setReversed(true)
                        .waitSeconds(2)
                        .turn(Math.toRadians(-90))
                        .lineTo(new Vector2d(0,-11))
                        .splineToConstantHeading(new Vector2d(28,-11), Math.toRadians(0))
                        .splineToConstantHeading(new Vector2d(48,-35), Math.toRadians(0))
                        .build();

                park = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                        .waitSeconds(2)
                        .strafeLeft(24)
                        .build();
                break;

            case BLUE_RIGHT:

                break;

            case BLUE_LEFT:
                startPose = new Pose2d(11, 61, Math.toRadians(-90));
                scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                        .splineTo(new Vector2d(11,38), Math.toRadians(-90))
                        .build();
                scoreBackBoard = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                        .setReversed(true)
                        .waitSeconds(2)
                        .turn(Math.toRadians(-90))
                        .splineToConstantHeading(new Vector2d(48,35), Math.toRadians(0))
                        .build();
                park = drive.trajectorySequenceBuilder(scoreBackBoard.end())
                        .waitSeconds(2)
                        .strafeRight(24)
                        .build();


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
