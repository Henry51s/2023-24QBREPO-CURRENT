package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.trajectorysequence.TrajectorySequence;

@Config
public class AutoTrajectories {

    public SampleMecanumDrive drive;

    public Pose2d startPose;
    public TrajectorySequence scoreSpikeMark;
    public TrajectorySequence scoreBackDrop;
    public TrajectorySequence park;
    public TrajectorySequence backupSpikeMark;
    public TrajectorySequence toNeutral;


    public enum AutoLocation {
        BLUE_SHORT,
        BLUE_LONG,
        RED_LONG,
        RED_SHORT
    }

    public enum SpikeMark {
        LEFT,
        MIDDLE,
        RIGHT
    }


    public AutoTrajectories(HardwareMap hw){
        drive = new SampleMecanumDrive(hw);

    }
    public Vector2d  poseToVector(Pose2d pose){
        Vector2d result = new Vector2d(pose.getX(), pose.getY());
        return result;
    }


    public static double neutral1X = -37.5, neutral1Y = 57, neutral1Heading = 0;
    public static double scoringX = 52.5, scoringY = 18.175  , scoringHeading = 0;


    /*public static double spikeMarkLX = 0, spikeMarkLY = 0, spikeMarkLHeading = 0;
    public static double spikeMarkMX = 0, spikeMarkMY = 0, spikeMarkMHeading = 0;
    public static double spikeMarkRX = 0, spikeMarkRY = 0, spikeMarkRHeading = 0;*/

    public static double[] spikeMarkL = {0,0,0};
    public static double[] spikeMarkM = {0,0,0};
    public static double[] spikeMarkR = {0,0,0};

    public static double[] backDropL = {0,0,0};
    public static double[] backDropM = {0,0,0};
    public static double[] backDropR = {0,0,0};

    Pose2d poseSpikeMarkL = new Pose2d(spikeMarkL[0], spikeMarkL[1], Math.toRadians(spikeMarkL[2]));
    Pose2d poseSpikeMarkM = new Pose2d(spikeMarkM[0], spikeMarkL[1], Math.toRadians(spikeMarkM[2]));
    Pose2d poseSpikemarkR = new Pose2d(spikeMarkR[0], spikeMarkR[1], Math.toRadians(spikeMarkR[2]));

    Pose2d poseBackDropL = new Pose2d(backDropL[0], backDropL[1], Math.toRadians(backDropL[2]));
    Pose2d poseBackDropM = new Pose2d(backDropM[0], backDropM[1], Math.toRadians(backDropM[2]));
    Pose2d poseBackDropR = new Pose2d(backDropR[0], backDropR[1], Math.toRadians(backDropR[2]));


    Pose2d neutralPose1 = new Pose2d(neutral1X, neutral1Y, Math.toRadians(neutral1Heading));
    Pose2d scoringPose = new Pose2d(scoringX, scoringY, Math.toRadians(scoringHeading));





    public void setPath(AutoLocation autoLocation, SpikeMark spikeMark){
        switch(autoLocation){
            case BLUE_LONG:

                startPose = new Pose2d(-37.5, 61, Math.toRadians(90));

                if(spikeMark == SpikeMark.RIGHT){

                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineTo(new Vector2d(-47 ,42))
                            .build();
                    backupSpikeMark = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .lineToConstantHeading(new Vector2d(-47,49))
                            .build();

                    toNeutral = drive.trajectorySequenceBuilder(backupSpikeMark.end())
                            //.lineToLinearHeading(neutralPose1)
                            .lineToConstantHeading(new Vector2d(-40, 54))
                            .turn(Math.toRadians(-(90 + 7)))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(new Pose2d(-40, 54, Math.toRadians(0)))
                            .lineToConstantHeading(new Vector2d(53, 50))
                            .lineToConstantHeading(poseToVector(scoringPose))
                            .build();


                }
                else if(spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineTo(new Vector2d(-37.5,33))
                            .build();
                    backupSpikeMark = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .lineToConstantHeading(new Vector2d(-37.5, 38))
                            .build();

                    toNeutral = drive.trajectorySequenceBuilder(backupSpikeMark.end())
                            .lineToConstantHeading(new Vector2d(-37.5, 57))
                            .turn(Math.toRadians(-(90 + 7)))
                            .build();

                    scoreBackDrop = drive.trajectorySequenceBuilder(new Pose2d(-40, 54, Math.toRadians(0)))
                            .lineToConstantHeading(new Vector2d(53, 50))
                            .waitSeconds(3)
                            .lineToConstantHeading(new Vector2d(52.5,23))
                            .build();

                }
                else if(spikeMark == SpikeMark.LEFT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToConstantHeading(new Vector2d(-34.8,29))
                            .turn(Math.toRadians(90))
                            .build();
                    backupSpikeMark = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .lineToConstantHeading(new Vector2d(-45, 29))
                            .build();

                    toNeutral = drive.trajectorySequenceBuilder(backupSpikeMark.end())
                            .lineToConstantHeading(new Vector2d(-40, 54))
                            .turn(Math.toRadians(-180))
                            .build();

                    scoreBackDrop = drive.trajectorySequenceBuilder(new Pose2d(-40, 54, Math.toRadians(0)))
                            .lineToConstantHeading(new Vector2d(53, 50))
                            .lineToConstantHeading(new Vector2d(50.5, 33))
                            .build();

                }
                break;
            case RED_LONG:




                startPose = new Pose2d(-37.5, -61, Math.toRadians(-90));


                if(spikeMark == SpikeMark.LEFT){

                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineTo(new Vector2d(-47 ,-42))
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
                            .lineToConstantHeading(new Vector2d(52, -18.175))
                            .build();

                }
                else if(spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineTo(new Vector2d(-37.5,-33))
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
                            .lineToConstantHeading(new Vector2d(52,-27.5))
                            .build();


                }
                else if(spikeMark == SpikeMark.RIGHT){

                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToConstantHeading(new Vector2d(-34.8,-29))
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
                            .lineToConstantHeading(new Vector2d(50.5, -33))
                            .build();


                }


                break;



            case BLUE_SHORT:



                startPose = new Pose2d(16,61,Math.toRadians(90));


                if(spikeMark == SpikeMark.LEFT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .splineToConstantHeading(new Vector2d(28, 40), Math.toRadians(0))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(55.5,37, Math.toRadians(0)))
                            .waitSeconds(0.5)
                            .build();
                }
                else if (spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(12,32, Math.toRadians(90)))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(56.5,31, Math.toRadians(0)))
                            .waitSeconds(0.5)
                            .build();
                }
                else if (spikeMark == SpikeMark.RIGHT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(20,30, Math.toRadians(0)))
                            .back(7)
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToConstantHeading(new Vector2d(55, 22))
                            .waitSeconds(0.5)
                            .build();
                }
                break;




            case RED_SHORT:



                startPose = new Pose2d(16, -61, Math.toRadians(-90));

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
                            .lineToLinearHeading(new Pose2d(12,-32, Math.toRadians(-90)))
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
