package org.firstinspires.ftc.teamcode.NonOpmodes.Pathing;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;


@Config
public class PointsOfInterest {


    private Pose2d arrayToPose(double[] coordinates){
        Pose2d resultPose = new Pose2d(coordinates[0], coordinates[1], Math.toRadians(coordinates[2]));
        return resultPose;
    }

    public static double[] redStartPose = {0,0,-180};
    public static double[] blueStartPose = {0,0,180};

    public static double[] redShortSpikeMarkL = {33,0,-90};
    public static double[] redShortSpikeMarkM = {39,-12,-90};
    public static double[] redShortSpikeMarkR = {32,-20,-90};


    public static double[] redLongSpikeMarkL = {40,7,0};
    public static double[] redLongSpikeMarkM = {57,0,0};
    public static double[] redLongSpikeMarkR = {29,-2,90};


    public static double[] blueLongSpikeMarkL = {32,-4,-90};
    public static double[] blueLongSpikeMarkM = {49,0,0};
    public static double[] blueLongSpikeMarkR = {34,4,90};




    public static double[] blueShortSpikeMarkL = {33,20,90};
    public static double[] blueShortSpikeMarkM = {39,-12,90};
    public static double[] blueShortSpikeMarkR = {32,0,90};


    public static double[] redShortBackDropL = {35,-32,-90};
    public static double[] redShortBackDropM = {29,-32,-90};
    public static double[] redShortBackDropR = {22,-32,-90};

    public static double[] blueShortBackDropL = {22,32,90};
    public static double[] blueShortBackDropM = {29,32,90};
    public static double[] blueShortBackDropR = {35,32,90};

    public static double[] redLongFirstIntake = {52,0,-90};
    public static double[] blueLongFirstIntake = {54, 0, 90};

    public static double[] redLongBackDropL = {30,-87,-90};
    public static double[] redLongBackDropM = {32,-87,-90};
    public static double[] redLongBackDropR = {34,-87,-90};



    public static double[] blueLongBackDropL = {26,88.5,90};
    public static double[] blueLongBackDropM = {33,88.5,90};
    public static double[] blueLongBackDropR = {40,88.5,90};

    public static double[] redShortCycleScore = {34,-32,-90};
    public static double[] blueShortCycleScore = {34,31,90};

    public static double[] redShortExtending1 = {51,-30,-90};
    public static double[] redShortExtending2 = {51,34,-87};

    public static double[] blueShortExtending1 = {51,30,90};
    public static double[] blueShortExtending2 = {51,-34,85};

    public static double[] redLongExtending1 = {52,-85,-90};
    public static double[] redLongExtending2 = {52,5,-90};

    public static double[] blueLongExtending1 = {57,5,90};
    public static double[] blueLongExtending2 = {45,80,90};
    //-------------------------------------------------------------

    public Pose2d poseRedStartPose = arrayToPose(redStartPose);
    public Pose2d poseBlueStartPose = arrayToPose(blueStartPose);

    public Pose2d poseRedShortCycleScore = arrayToPose(redShortCycleScore);
    public Pose2d poseBlueShortCycleScore = arrayToPose(blueShortCycleScore);

    public Pose2d poseRedShortSpikeMarkL = arrayToPose(redShortSpikeMarkL);
    public Pose2d poseRedShortSpikeMarkM = arrayToPose(redShortSpikeMarkM);
    public Pose2d poseRedShortSpikeMarkR = arrayToPose(redShortSpikeMarkR);

    public Pose2d poseRedLongSpikeMarkL = arrayToPose(redLongSpikeMarkL);
    public Pose2d poseRedLongSpikeMarkM = arrayToPose(redLongSpikeMarkM);
    public Pose2d poseRedLongSpikeMarkR = arrayToPose(redLongSpikeMarkR);

    public Pose2d poseRedLongFirstIntake = arrayToPose(redLongFirstIntake);
    public Pose2d poseBlueLongFirstIntake = arrayToPose(blueLongFirstIntake);

    public Pose2d poseBlueShortSpikeMarkL = arrayToPose(blueShortSpikeMarkL);
    public Pose2d poseBlueShortSpikeMarkM = arrayToPose(blueShortSpikeMarkM);
    public Pose2d poseBlueShortSpikeMarkR = arrayToPose(blueShortSpikeMarkR);

    public Pose2d poseBlueLongSpikeMarkL = arrayToPose(blueLongSpikeMarkL);
    public Pose2d poseBlueLongSpikeMarkM = arrayToPose(blueLongSpikeMarkM);
    public Pose2d poseBlueLongSpikeMarkR = arrayToPose(blueLongSpikeMarkR);

    public Pose2d poseRedShortBackDropL = arrayToPose(redShortBackDropL);
    public Pose2d poseRedShortBackDropM = arrayToPose(redShortBackDropM);
    public Pose2d poseRedShortBackDropR = arrayToPose(redShortBackDropR);

    public Pose2d poseRedLongBackDropL = arrayToPose(redLongBackDropL);
    public Pose2d poseRedLongBackDropM = arrayToPose(redLongBackDropM);
    public Pose2d poseRedLongBackDropR = arrayToPose(redLongBackDropR);

    public Pose2d poseBlueShortBackDropL = arrayToPose(blueShortBackDropL);
    public Pose2d poseBlueShortBackDropM = arrayToPose(blueShortBackDropM);
    public Pose2d poseBlueShortBackDropR = arrayToPose(blueShortBackDropR);

    public Pose2d poseBlueLongBackDropL = arrayToPose(blueLongBackDropL);
    public Pose2d poseBlueLongBackDropM = arrayToPose(blueLongBackDropM);
    public Pose2d poseBlueLongBackDropR = arrayToPose(blueLongBackDropR);

    public Pose2d poseRedShortExtending1 = arrayToPose(redShortExtending1);
    public Pose2d poseRedShortExtending2 = arrayToPose(redShortExtending2);

    public Pose2d poseRedLongExtending1 = arrayToPose(redLongExtending1);
    public Pose2d poseRedLongExtending2 = arrayToPose(redLongExtending2);

    public Pose2d poseBlueLongExtending1 = arrayToPose(blueLongExtending1);
    public Pose2d poseBlueLongExtending2 = arrayToPose(blueLongExtending2);

    public Pose2d poseBlueShortExtending1 = arrayToPose(blueShortExtending1);
    public Pose2d poseBlueShortExtending2 = arrayToPose(blueShortExtending2);





}
