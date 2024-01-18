package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.trajectorysequence.TrajectorySequence;

public class AutoTrajectories {

    public SampleMecanumDrive drive;

    public Pose2d startPose;
    public TrajectorySequence scoreSpikeMark;
    public TrajectorySequence scoreBackBoard;
    public TrajectorySequence backBoardOffset;
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
                startPose = new Pose2d(15, -61, Math.toRadians(90));

                break;

            case RED_LEFT: //Work on this for now
                startPose = new Pose2d(-35, -61, Math.toRadians(90));
            case BLUE_RIGHT:

                break;

            case BLUE_LEFT:
                startPose = new Pose2d(11, 61, Math.toRadians(270));
                break;
        }
        drive.setPoseEstimate(startPose);

    }
    public void setSpikeMark(SpikeMark spikeMark){
        this.spikeMark = spikeMark;
        switch(spikeMark){
            case LEFT:
                //Define toSpikeMark trajectories
                scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                        .setReversed(false)
                        .forward(26)
                        .turn(Math.toRadians(-90))
                        .build();
                backBoardOffset = drive.trajectorySequenceBuilder(startPose)
                        .strafeLeft(2)
                        .build();
                break;
            case RIGHT:
                scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                        .setReversed(false)
                        .forward(26)
                        .turn(Math.toRadians(90))
                        .build();
                backBoardOffset = drive.trajectorySequenceBuilder(startPose)
                        .strafeRight(2)
                        .build();
                break;
            case MIDDLE:
                scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                        .setReversed(false)
                        .back(26)
                        .build();
                backBoardOffset = drive.trajectorySequenceBuilder(startPose)
                        .strafeLeft(1)
                        .build();

                break;
        }
    }
    public void setAutoPath(AutoLocation autoLocation){ //Must be called AFTER setSpikeMark
        switch(autoLocation){
            case RED_LEFT:
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
            case RED_RIGHT:
                scoreBackBoard = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                        .setReversed(false)
                        .waitSeconds(2)
                        .turn(Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(48,-35), Math.toRadians(0))
                        .build();
                park = drive.trajectorySequenceBuilder(scoreBackBoard.end())
                        .waitSeconds(2)
                        .strafeLeft(24)
                        .build();
                break;
            case BLUE_LEFT:
                scoreBackBoard = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                        .setReversed(false)
                        .waitSeconds(2)
                        .turn(Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(48,35), Math.toRadians(0))
                        .build();
                park = drive.trajectorySequenceBuilder(scoreBackBoard.end())
                        .waitSeconds(2)
                        .strafeRight(24)
                        .build();
                break;
            case BLUE_RIGHT:
                break;
        }
    }
}
