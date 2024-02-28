package org.firstinspires.ftc.teamcode.NonOpmodes.Pathing;

import static org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.DriveConstants.MAX_ANG_VEL;
import static org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.DriveConstants.MAX_VEL;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.MAX_TURN_SPEED;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TranslationalVelocityConstraint;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoLocation;
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.SpikeMark;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.trajectorysequence.TrajectorySequence;

import java.util.Arrays;

@Config
public class Autonomous {



    public Pose2d redStartPose;
    public Pose2d blueStartPose;
    public TrajectorySequence scoreSpikeMark;
    public TrajectorySequence scoreBackDrop;
    public TrajectorySequence park;
    public TrajectorySequence toExtend;
    public TrajectorySequence extending;
    public TrajectorySequence extendToBackDrop;
    public TrajectorySequence cycleToExtend;
    public TrajectorySequence cycleToExtending;
    public TrajectorySequence firstIntake;

    private PointsOfInterest pointsOfInterest = new PointsOfInterest();
    private SampleMecanumDrive drive;


    private TrajectoryVelocityConstraint slow = new MinVelocityConstraint(Arrays.asList(
            new TranslationalVelocityConstraint(MAX_VEL),
            new AngularVelocityConstraint(Math.toRadians(MAX_TURN_SPEED))
    ));
    private TrajectoryVelocityConstraint normal = new MinVelocityConstraint(Arrays.asList(
            new TranslationalVelocityConstraint(MAX_VEL),
            new AngularVelocityConstraint(Math.toRadians(MAX_ANG_VEL))
    ));



    public Autonomous(HardwareMap hw){
        drive = new SampleMecanumDrive(hw);

    }
    public Vector2d  poseToVector(Pose2d pose){
        Vector2d result = new Vector2d(pose.getX(), pose.getY());
        return result;
    }
    public Pose2d normalizeEndPose(Pose2d pose){
        double poseHeading = pose.getHeading();
        double closestHeading = 0;

        if(Math.abs(poseHeading) < 10){
            closestHeading = 0;
        }
        else if(Math.abs(Math.abs(poseHeading) - 90) < 10){
            closestHeading = 90 * Math.signum(poseHeading);
        }
        else if(Math.abs(Math.abs(poseHeading) - 180) < 10){
            closestHeading = 180 * Math.signum(poseHeading);
        }

        Pose2d normalizedPose = new Pose2d(pose.getX(), pose.getY(), Math.toRadians(closestHeading));
        return normalizedPose;
    }
    public SampleMecanumDrive getDrive(){
        return drive;
    }



    public void setPath(AutoLocation autoLocation, SpikeMark spikeMark){
        redStartPose = pointsOfInterest.poseRedStartPose;
        blueStartPose = pointsOfInterest.poseBlueStartPose;


        switch(autoLocation){
            case BLUE_LONG:

                if(spikeMark == SpikeMark.RIGHT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(blueStartPose)
                            .setReversed(true)

                            .lineToLinearHeading(pointsOfInterest.poseBlueLongSpikeMarkR)
                            .build();
                    firstIntake = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongFirstIntake)
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(firstIntake.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongExtending2)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongBackDropR)
                            .build();
                    toExtend = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongExtending2)
                            .build();
                    extendToBackDrop = drive.trajectorySequenceBuilder(toExtend.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongBackDropR)
                            .build();



                }
                else if(spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(blueStartPose)
                            .setReversed(true)
                            .setVelConstraint(slow)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongSpikeMarkM)
                            .resetVelConstraint()
                            .build();
                    firstIntake = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)

                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseBlueLongFirstIntake))
                            .turn(pointsOfInterest.poseBlueLongFirstIntake.getHeading())

                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(firstIntake.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongExtending2)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongBackDropM)
                            .build();
                    toExtend = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongExtending2)
                            .build();
                    extendToBackDrop = drive.trajectorySequenceBuilder(toExtend.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongBackDropR)
                            .build();


                }
                else if(spikeMark == SpikeMark.LEFT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(blueStartPose)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongSpikeMarkL)
                            .back(5)
                            .build();
                    firstIntake = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            //.turn(pointsOfInterest.poseBlueLongFirstIntake.getHeading())
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongFirstIntake)
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(firstIntake.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongExtending2)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongBackDropL)
                            .build();
                    toExtend = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongExtending2)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongExtending1)
                            .build();
                    extendToBackDrop = drive.trajectorySequenceBuilder(toExtend.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongExtending2)
                            .lineToLinearHeading(pointsOfInterest.poseBlueLongBackDropL)
                            .build();
                }
                break;
            case RED_LONG:


                if(spikeMark == SpikeMark.LEFT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(redStartPose)
                            .setReversed(true)
                            //.lineToConstantHeading(poseToVector(pointsOfInterest.poseRedLongSpikeMarkL))
                            //.turn(Math.toRadians(180))
                            .lineToLinearHeading(pointsOfInterest.poseRedLongSpikeMarkL)
                            .back(5)
                            .build();
                    firstIntake = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            //.turn(pointsOfInterest.poseRedLongFirstIntake.getHeading())
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseRedLongFirstIntake))
                            .turn(pointsOfInterest.poseRedLongFirstIntake.getHeading())
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(firstIntake.end())
                            .setReversed(true)
                            //.lineToLinearHeading(pointsOfInterest.poseRedLongExtending2)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongBackDropL)
                            .build();
                    toExtend = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongExtending2)
                            .build();
                    extendToBackDrop = drive.trajectorySequenceBuilder(toExtend.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongBackDropL)
                            .build();


                }
                else if(spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(redStartPose)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongSpikeMarkM)
                            .build();
                    firstIntake = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setVelConstraint(slow)
                            .setReversed(true)
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseRedLongFirstIntake))
                            .turn(pointsOfInterest.poseRedLongFirstIntake.getHeading())
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(firstIntake.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongExtending1)
                            //.lineToLinearHeading(pointsOfInterest.poseRedLongExtending2)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongBackDropM)
                            .build();
                    toExtend = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongExtending2)
                            .build();
                    extendToBackDrop = drive.trajectorySequenceBuilder(toExtend.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongBackDropM)
                            .build();


                }
                else if(spikeMark == SpikeMark.RIGHT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(redStartPose)
                            .setReversed(true)
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseRedLongSpikeMarkR))
                            .turn(-pointsOfInterest.poseRedLongSpikeMarkR.getHeading())
                            .build();
                    firstIntake = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .turn(pointsOfInterest.poseRedLongFirstIntake.getHeading())
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(firstIntake.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongExtending2)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongBackDropR)
                            .build();
                    toExtend = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongExtending2)
                            .build();
                    extendToBackDrop = drive.trajectorySequenceBuilder(toExtend.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseRedLongBackDropR)
                            .build();



                }


                break;



            case BLUE_SHORT:


                if(spikeMark == SpikeMark.LEFT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(blueStartPose)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortSpikeMarkL)
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortBackDropL)
                            .build();
                    toExtend = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortExtending1)

                            .build();
                    extending = drive.trajectorySequenceBuilder(toExtend.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortExtending2)
                            .build();

                    cycleToExtend = drive.trajectorySequenceBuilder(pointsOfInterest.poseBlueShortCycleScore)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortExtending1)
                            .build();
                    cycleToExtending = drive.trajectorySequenceBuilder(pointsOfInterest.poseBlueShortExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortExtending2)
                            .build();

                    extendToBackDrop = drive.trajectorySequenceBuilder(pointsOfInterest.poseBlueShortExtending2)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortCycleScore)
                            .build();

                }
                else if (spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(blueStartPose)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortSpikeMarkM)
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortBackDropM)
                            .build();
                    toExtend = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortExtending1)

                            .build();
                    extending = drive.trajectorySequenceBuilder(toExtend.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortExtending2)
                            .build();

                    cycleToExtend = drive.trajectorySequenceBuilder(pointsOfInterest.poseBlueShortCycleScore)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortExtending1)
                            .build();
                    cycleToExtending = drive.trajectorySequenceBuilder(pointsOfInterest.poseBlueShortExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortExtending2)
                            .build();

                    extendToBackDrop = drive.trajectorySequenceBuilder(pointsOfInterest.poseBlueShortExtending2)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortCycleScore)
                            .build();

                }
                else if (spikeMark == SpikeMark.RIGHT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(blueStartPose)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortSpikeMarkR)
                            .back(5)
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortBackDropR)
                            .build();
                    toExtend = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortExtending1)

                            .build();
                    extending = drive.trajectorySequenceBuilder(toExtend.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortExtending2)
                            .build();

                    cycleToExtend = drive.trajectorySequenceBuilder(pointsOfInterest.poseBlueShortCycleScore)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortExtending1)
                            .build();
                    cycleToExtending = drive.trajectorySequenceBuilder(pointsOfInterest.poseBlueShortExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortExtending2)
                            .build();

                    extendToBackDrop = drive.trajectorySequenceBuilder(pointsOfInterest.poseBlueShortExtending2)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseBlueShortCycleScore)
                            .build();

                }
                break;




            case RED_SHORT:
                if(spikeMark == SpikeMark.LEFT){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(redStartPose)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortSpikeMarkL)
                            .back(3)
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortBackDropL)
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
                else if(spikeMark == SpikeMark.MIDDLE){
                    scoreSpikeMark = drive.trajectorySequenceBuilder(redStartPose)
                            .setReversed(true)
                            .lineToConstantHeading(poseToVector(pointsOfInterest.poseRedShortSpikeMarkM))
                            .turn(Math.toRadians(90))
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
                    scoreSpikeMark = drive.trajectorySequenceBuilder(redStartPose)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortSpikeMarkR)
                            .build();
                    scoreBackDrop = drive.trajectorySequenceBuilder(scoreSpikeMark.end())
                            .setReversed(true)

                            .lineToLinearHeading(pointsOfInterest.poseRedShortBackDropR)
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

                    /*extendToBackDrop = drive.trajectorySequenceBuilder(pointsOfInterest.poseRedShortExtending2)
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortCycleScore)
                            .build();*/
                    extendToBackDrop = drive.trajectorySequenceBuilder(new Pose2d(pointsOfInterest.poseRedShortExtending2.getX(), pointsOfInterest.poseRedShortExtending2.getY(), Math.toRadians(-90)))
                            .setReversed(true)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortExtending1)
                            .lineToLinearHeading(pointsOfInterest.poseRedShortCycleScore)
                            .build();

                    park = drive.trajectorySequenceBuilder(scoreBackDrop.end())
                            .setReversed(true)
                            .forward(10)
                            .build();
                }
                break;
        }
        drive.setPoseEstimate(redStartPose);
    }
}
