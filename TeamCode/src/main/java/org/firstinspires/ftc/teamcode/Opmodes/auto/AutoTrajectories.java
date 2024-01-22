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
    public static double x1 = 22, y1 = 40, heading1 = 0;


    public static double x2 = -16, y2 = -31, heading2 = -90;


    public static SpikeMark spikeMark = SpikeMark.MIDDLE;
    public void setPath(AutoLocation autoLocation, SpikeMark spikeMark){
        switch(autoLocation){
            case BLUE_LONG:



                startPose = new Pose2d(-37.5, 61, Math.toRadians(90));
                if(spikeMark == SpikeMark.LEFT){

                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(-37.5,31, Math.toRadians(90)))
                            .turn(Math.toRadians(-90))
                            .lineTo(new Vector2d(-16, 31))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(51,31, Math.toRadians(0)))
                            .build();

                }
                else if(spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(-37.5,31, Math.toRadians(90)))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .turn(Math.toRadians(-90))
                            .lineToLinearHeading(new Pose2d(51,31, Math.toRadians(0)))
                            .build();

                }
                else if(spikeMark == SpikeMark.RIGHT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(-37.5,31, Math.toRadians(90)))
                            .turn(Math.toRadians(-90))
                            .lineTo(new Vector2d(-20, 31))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(51,31, Math.toRadians(0)))
                            .build();

                }
                break;
            case RED_LONG:



                startPose = new Pose2d(-37.5, -61, Math.toRadians(-90));
                if(spikeMark == SpikeMark.LEFT){

                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(-37.5,-31, Math.toRadians(-90)))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .turn(Math.toRadians(90))
                            .lineToLinearHeading(new Pose2d(51,-31, Math.toRadians(0)))
                            .build();

                }
                else if(spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(-37.5,-31, Math.toRadians(-90)))
                            .turn(Math.toRadians(90))
                            .lineTo(new Vector2d(-27, -31))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(51,-31, Math.toRadians(0)))
                            .build();

                }
                else if(spikeMark == SpikeMark.RIGHT){

                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(-37.5,-31, Math.toRadians(-90)))
                            .turn(Math.toRadians(90))
                            .lineTo(new Vector2d(x2, -31))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(51,-31, Math.toRadians(0)))
                            .build();

                }



                break;



            case BLUE_SHORT:



                startPose = new Pose2d(9,61,Math.toRadians(90));
                if(spikeMark == SpikeMark.LEFT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(22,45, Math.toRadians(90)))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(56,35, Math.toRadians(0)))
                            .build();
                }
                else if (spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(12,35, Math.toRadians(90)))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(56,29, Math.toRadians(0)))
                            .build();
                }
                else if (spikeMark == SpikeMark.RIGHT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(22,45, Math.toRadians(90)))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(55.5,22, Math.toRadians(0)))
                            .build();

                }
                break;




            case RED_SHORT:



                startPose = new Pose2d(16, -61, Math.toRadians(-90));
                if(spikeMark == SpikeMark.LEFT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .strafeRight(2)
                            .lineToLinearHeading(new Pose2d(x1,-30, Math.toRadians(0)))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToConstantHeading(new Vector2d(55,-20))
                            .waitSeconds(0.5)
                            .back(1)
                            .build();

                }
                else if(spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .strafeRight(2)
                            .lineToLinearHeading(new Pose2d(12,-35, Math.toRadians(-90)))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(56.5,-30, Math.toRadians(0)))
                            .waitSeconds(0.5)
                            .back(1)
                            .build();


                }
                else if(spikeMark == SpikeMark.RIGHT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .strafeRight(2)
                            .lineToLinearHeading(new Pose2d(22,-45, Math.toRadians(-90)))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(new Pose2d(56,-33, Math.toRadians(0)))
                            .waitSeconds(0.5)
                            .back(1)
                            .build();

                }
                break;




        }
        drive.setPoseEstimate(startPose);
    }
}
