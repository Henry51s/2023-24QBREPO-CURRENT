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
    public TrajectorySequence extendToBackDrop;
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
        startPose = pointsOfInterest.poseStartPose;
        switch(autoLocation){
            case BLUE_LONG:

                if(spikeMark == SpikeMark.RIGHT){

                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)

                            .build();
                    backupSpikeMark = drive.trajectorySequenceBuilder(scoreSpikeMark.end())

                            .build();

                    toNeutral = drive.trajectorySequenceBuilder(backupSpikeMark.end())

                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(new Pose2d(-40, 54, Math.toRadians(0)))

                            .build();


                }
                else if(spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)

                            .build();
                    backupSpikeMark = drive.trajectorySequenceBuilder(scoreSpikeMark.end())

                            .build();

                    toNeutral = drive.trajectorySequenceBuilder(backupSpikeMark.end())

                            .build();

                    scoreBackDrop = drive.trajectorySequenceBuilder(new Pose2d(-40, 54, Math.toRadians(0)))

                            .build();

                }
                else if(spikeMark == SpikeMark.LEFT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)

                            .build();
                    backupSpikeMark = drive.trajectorySequenceBuilder(scoreSpikeMark.end())

                            .build();

                    toNeutral = drive.trajectorySequenceBuilder(backupSpikeMark.end())

                            .build();

                    scoreBackDrop = drive.trajectorySequenceBuilder(toNeutral.end())

                            .build();
                }
                break;
            case RED_LONG:


                if(spikeMark == SpikeMark.LEFT){

                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)

                            .build();
                    backupSpikeMark = drive.trajectorySequenceBuilder(scoreSpikeMark.end())

                            .build();

                    toNeutral = drive.trajectorySequenceBuilder(backupSpikeMark.end())

                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(toNeutral.end())

                            .build();

                }
                else if(spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)

                            .build();
                    backupSpikeMark = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .build();

                    toNeutral = drive.trajectorySequenceBuilder(backupSpikeMark.end())

                            .build();

                    scoreBackDrop = drive.trajectorySequenceBuilder(toNeutral.end())

                            .build();


                }
                else if(spikeMark == SpikeMark.RIGHT){

                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)

                            .build();
                    backupSpikeMark = drive.trajectorySequenceBuilder(scoreSpikeMark.end())

                            .build();

                    toNeutral = drive.trajectorySequenceBuilder(backupSpikeMark.end())

                            .build();

                    scoreBackDrop = drive.trajectorySequenceBuilder(toNeutral.end())

                            .build();


                }


                break;



            case BLUE_SHORT:


                if(spikeMark == SpikeMark.LEFT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortSpikeMarkL)
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortBackDropL)
                            .build();
                }
                else if (spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseBlueShortSpikeMarkM))
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortBackDropM)
                            .build();
                }
                else if (spikeMark == SpikeMark.RIGHT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(startPose)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortSpikeMarkR)
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortBackDropR)
                            .build();
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
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseRedShortExtending1))
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseRedShortExtending2))
                            .build();
                    extendToBackDrop = drive.trajectorySequenceBuilder(toExtend.end())
                            .setReversed(true)
                            .lineToConstantHeading(new Vector2d(pointsOfInterest.poseRedShortExtending2.getX()+5, pointsOfInterest.poseRedShortExtending2.getY()))
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseRedShortBackDropM))
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
