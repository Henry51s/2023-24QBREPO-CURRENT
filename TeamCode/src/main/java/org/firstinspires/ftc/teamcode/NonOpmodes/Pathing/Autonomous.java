package org.firstinspires.ftc.teamcode.NonOpmodes.Pathing;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoLocation;
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.SpikeMark;
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
    public TrajectorySequence toExtend;
    public TrajectorySequence extending;
    public TrajectorySequence extendToBackDrop;
    public TrajectorySequence cycleToExtend;
    public TrajectorySequence cycleToExtending;
    public TrajectorySequence parkLeft;
    public TrajectorySequence firstIntake;

    private PointsOfInterest pointsOfInterest = new PointsOfInterest();
    private SampleMecanumDrive drive;


    public Autonomous(HardwareMap hw){
        drive = new SampleMecanumDrive(hw);

    }
    public Vector2d  poseToVector(Pose2d pose){
        Vector2d result = new Vector2d(pose.getX(), pose.getY());
        return result;
    }
    public double getPoseHeading(Pose2d pose){
        return pose.getHeading();
    }
    public SampleMecanumDrive getDrive(){
        return drive;
    }



    public void setPath(AutoLocation autoLocation, SpikeMark spikeMark){

        parkLeft = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .strafeLeft(10)
                .build();

        startPose = pointsOfInterest.poseStartPose;
        switch(autoLocation){
            case BLUE_LONG:

                if(spikeMark == SpikeMark.RIGHT){



                }
                else if(spikeMark == SpikeMark.MIDDLE){


                }
                else if(spikeMark == SpikeMark.LEFT){

                }
                break;
            case RED_LONG:


                if(spikeMark == SpikeMark.LEFT){



                }
                else if(spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongSpikeMarkM)
                            .build();
                    firstIntake = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .turn(pointsOfInterest.poseRedLongFirstIntake.getHeading())
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(firstIntake.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongBackDropM)
                            .build();


                }
                else if(spikeMark == SpikeMark.RIGHT){




                }


                break;



            case BLUE_SHORT:


                if(spikeMark == SpikeMark.LEFT){

                }
                else if (spikeMark == SpikeMark.MIDDLE){

                }
                else if (spikeMark == SpikeMark.RIGHT){

                }
                break;




            case RED_SHORT:
                if(spikeMark == SpikeMark.LEFT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortSpikeMarkL)
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortBackDropL)
                            .build();
                    toExtend = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .setReversed(true)
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseRedShortExtending2))
                            .build();
                    park = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .setReversed(true)
                            .forward(10)
                            .build();

                }
                else if(spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortSpikeMarkM)
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)

                            .lineToLinearHeading(pointsOfInterest.poseRedShortBackDropM)
                            .build();
                    toExtend = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortExtending1)
                            .build();
                    extending = drive.trajectorySequenceBuilder(toExtend.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortExtending2)
                            .build();

                    cycleToExtend = drive.trajectorySequenceBuilder(pointsOfInterest.poseRedShortCycleScore)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortExtending1)
                            .build();
                    cycleToExtending = drive.trajectorySequenceBuilder(pointsOfInterest.poseRedShortExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortExtending2)
                            .build();

                    extendToBackDrop = drive.trajectorySequenceBuilder(pointsOfInterest.poseRedShortExtending2)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortCycleScore)
                            .build();
                    park = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .setReversed(true)
                            .forward(10)
                            .build();


                }
                else if(spikeMark == SpikeMark.RIGHT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortSpikeMarkR)
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortBackDropR)
                            .build();
                    toExtend = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .setReversed(true)
                            //.lineToLinearHeading(pointsOfInterest.poseRedShortExtending)
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseRedShortExtending2))
                            .build();

                    park = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .setReversed(true)
                            .forward(10)
                            .build();
                }
                break;
        }
        drive.setPoseEstimate(startPose);
    }
}
