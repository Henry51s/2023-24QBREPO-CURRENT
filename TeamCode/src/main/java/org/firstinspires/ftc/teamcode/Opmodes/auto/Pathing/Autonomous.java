package org.firstinspires.ftc.teamcode.Opmodes.auto.Pathing;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.trajectorysequence.TrajectorySequence;

@Config
public class Autonomous {



    public Pose2d startPose;
    public TrajectorySequence scoreSpikeMark;
    public TrajectorySequence scoreBackDrop;
    public TrajectorySequence park;
    public TrajectorySequence backupSpikeMark;
    public TrajectorySequence toNeutral;
    private PointsOfInterest pointsOfInterest = new PointsOfInterest();
    private SampleMecanumDrive drive;


    public Autonomous(HardwareMap hw){
        drive = new SampleMecanumDrive(hw);

    }
    public Vector2d  poseToVector(Pose2d pose){
        Vector2d result = new Vector2d(pose.getX(), pose.getY());
        return result;
    }
    public SampleMecanumDrive getDrive(){
        return drive;
    }


    public static double neutral1X = -37.5, neutral1Y = -57, neutral1Heading = 7;
    public static double scoringX = 52.5, scoringY = 18.175  , scoringHeading = 0;


    Pose2d neutralPose1 = new Pose2d(neutral1X, neutral1Y, Math.toRadians(neutral1Heading));
    Pose2d scoringPose = new Pose2d(scoringX, scoringY, Math.toRadians(scoringHeading));





    public void setPath(AutoLocation autoLocation, SpikeMark spikeMark){
        switch(autoLocation){
            case BLUE_LONG:

                startPose = pointsOfInterest.poseBlueLongStart;

                if(spikeMark == SpikeMark.RIGHT){

                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineTo(poseToVector(pointsOfInterest.poseBlueLongSpikeMarkR))
                            .build();
                    backupSpikeMark = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .lineToConstantHeading(new Vector2d(-47,49))
                            .build();

                    toNeutral = drive.trajectorySequenceBuilder(backupSpikeMark.end())
                            //.lineToLinearHeading(neutralPose1)
                            .lineToConstantHeading(new Vector2d(-40, 53))
                            .turn(Math.toRadians(-(90 + neutral1Heading)))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(new Pose2d(-40, 54, Math.toRadians(0)))
                            .lineToConstantHeading(new Vector2d(53, 50))
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseBlueBackDropR))
                            .build();


                }
                else if(spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineTo(poseToVector(pointsOfInterest.poseBlueLongSpikeMarkM))
                            .build();
                    backupSpikeMark = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .lineToConstantHeading(new Vector2d(-37.5, 38))
                            .build();

                    toNeutral = drive.trajectorySequenceBuilder(backupSpikeMark.end())
                            .lineToConstantHeading(new Vector2d(-37.5, 56))
                            .turn(Math.toRadians(-(90 + neutral1Heading)))
                            .build();

                    scoreBackDrop = drive.trajectorySequenceBuilder(new Pose2d(-40, 54, Math.toRadians(0)))
                            .lineToConstantHeading(new Vector2d(53, 50))
                            .waitSeconds(3)
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseBlueBackDropM))
                            .build();

                }
                else if(spikeMark == SpikeMark.LEFT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseBlueLongSpikeMarkL))
                            .turn(Math.toRadians(90))
                            .build();
                    backupSpikeMark = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .lineToConstantHeading(new Vector2d(-47, 29))
                            .build();

                    toNeutral = drive.trajectorySequenceBuilder(backupSpikeMark.end())
                            .lineToConstantHeading(new Vector2d(-47, 50))
                            .turn(Math.toRadians(-180))
                            .build();

                    scoreBackDrop = drive.trajectorySequenceBuilder(new Pose2d(-40, 52, Math.toRadians(0)))
                            .lineToConstantHeading(new Vector2d(48.5, 50))
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseBlueBackDropL))
                            .build();

                }
                break;
            case RED_LONG:




                startPose = pointsOfInterest.poseRedLongStart;


                if(spikeMark == SpikeMark.LEFT){

                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineTo(poseToVector(pointsOfInterest.poseRedLongSpikeMarkL))
                            .build();
                    backupSpikeMark = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .lineToConstantHeading(new Vector2d(-47,-49))
                            .build();

                    toNeutral = drive.trajectorySequenceBuilder(backupSpikeMark.end())
                            //.lineToLinearHeading(neutralPose1)
                            .lineToConstantHeading(poseToVector(neutralPose1))
                            .turn(Math.toRadians(90 + 7))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(new Pose2d(-37.5, -57, Math.toRadians(0)))
                            .lineToConstantHeading(new Vector2d(53, -50))
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseRedBackDropL))
                            .build();

                }
                else if(spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineTo(poseToVector(pointsOfInterest.poseRedLongSpikeMarkM))
                            .build();
                    backupSpikeMark = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .lineToConstantHeading(new Vector2d(-37.5, -38))
                            .build();

                    toNeutral = drive.trajectorySequenceBuilder(backupSpikeMark.end())
                            .lineToConstantHeading(new Vector2d(-37.5, -57))
                            .turn(Math.toRadians(90 + 7))
                            .build();

                    scoreBackDrop = drive.trajectorySequenceBuilder(new Pose2d(-37.5, -57, Math.toRadians(0)))
                            .lineToConstantHeading(new Vector2d(53, -50))
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseRedBackDropM))
                            .build();


                }
                else if(spikeMark == SpikeMark.RIGHT){

                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseRedLongSpikeMarkM))
                            .turn(Math.toRadians(-90))
                            .build();
                    backupSpikeMark = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .lineToConstantHeading(new Vector2d(-45, -29))
                            .build();

                    toNeutral = drive.trajectorySequenceBuilder(backupSpikeMark.end())
                            .lineToConstantHeading(poseToVector(neutralPose1))
                            .turn(Math.toRadians(180))
                            .build();

                    scoreBackDrop = drive.trajectorySequenceBuilder(new Pose2d(-37.5, -57, Math.toRadians(0)))
                            .lineToConstantHeading(new Vector2d(53, -50))
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseRedBackDropR))
                            .build();


                }


                break;



            case BLUE_SHORT:



                startPose = pointsOfInterest.poseBlueShortStart;


                if(spikeMark == SpikeMark.LEFT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .splineToConstantHeading(poseToVector(pointsOfInterest.poseBlueShortSpikeMarkL), Math.toRadians(0))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueBackDropL)
                            .waitSeconds(0.5)
                            .build();
                }
                else if (spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortSpikeMarkM)
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueBackDropM)
                            .waitSeconds(0.5)
                            .build();
                }
                else if (spikeMark == SpikeMark.RIGHT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortSpikeMarkR)
                            .back(7)
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseBlueBackDropR))
                            .waitSeconds(0.5)
                            .build();
                }
                break;




            case RED_SHORT:



                startPose = pointsOfInterest.poseRedShortStart;

                if(spikeMark == SpikeMark.LEFT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(20,-30, Math.toRadians(0)))
                            .back(5)
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToConstantHeading(new Vector2d(55, -22))
                            .waitSeconds(0.5)
                            .build();
                    park = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .lineToConstantHeading(new Vector2d(49, -11))
                            .build();

                }
                else if(spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(20,-30, Math.toRadians(0)))
                            .back(5)
                            .lineToConstantHeading(new Vector2d(55, -22))
                            .waitSeconds(0.5)
                            .lineToConstantHeading(new Vector2d(40,-12))
                            .lineToConstantHeading(new Vector2d(-10,-12))
                            .lineToConstantHeading(new Vector2d(40,-12))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(56.5,-31, Math.toRadians(0)))
                            .waitSeconds(0.5)
                            .build();
                    park = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .lineToConstantHeading(new Vector2d(49, -55))
                            .build();


                }
                else if(spikeMark == SpikeMark.RIGHT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            //.lineToLinearHeading(new Pose2d(34, -40, Math.toRadians(-90)))
                            //.splineToConstantHeading(new Vector2d(28,-40), Math.toRadians(-90))
                            //.splineTo(new Vector2d(32,-40), Math.toRadians(-90))
                            //.splineToLinearHeading(new Pose2d(32,-40, Math.toRadians(-90)), Math.toRadians(-90))
                            .splineToConstantHeading(new Vector2d(28, -40), Math.toRadians(0))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(55.5,-37, Math.toRadians(0)))
                            .waitSeconds(0.5)
                            .build();
                    park = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .lineToConstantHeading(new Vector2d(49, -55))
                            .build();
                }
                break;
        }
        drive.setPoseEstimate(startPose);
    }
}
