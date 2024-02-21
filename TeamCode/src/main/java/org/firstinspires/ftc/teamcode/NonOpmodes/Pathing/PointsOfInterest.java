package org.firstinspires.ftc.teamcode.NonOpmodes.Pathing;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;


@Config
public class PointsOfInterest {


    private Pose2d arrayToPose(double[] coordinates){
        Pose2d resultPose = new Pose2d(coordinates[0], coordinates[1], Math.toRadians(coordinates[2]));
        return resultPose;
    }

    public static double[] startPose = {0,0,-180};
    public static double[] redShortSpikeMarkL = {33,-7,-90};
    public static double[] redShortSpikeMarkM = {42,-16,-90};
    public static double[] redShortSpikeMarkR = {32,-22,-90};


    public static double[] redLongSpikeMarkL = {0,0,0};
    public static double[] redLongSpikeMarkM = {57,0,0};
    public static double[] redLongSpikeMarkR = {0,0,0};
    public static double[] preBackDropRedLong = {0,0,0};



    public static double[] blueShortSpikeMarkL = {0,0,0};
    public static double[] blueShortSpikeMarkM = {0,0,0};
    public static double[] blueShortSpikeMarkR = {0,0,0};



    public static double[] blueLongSpikeMarkL = {0,0,0};
    public static double[] blueLongSpikeMarkM = {0,0,0};
    public static double[] blueLongSpikeMarkR = {0,0,0};
    public static double[] preBackDropBlueLong = {0,0,0};



    public static double[] redShortBackDropL = {40,-39,-90};
    public static double[] redShortBackDropM = {29,-39,-90};
    public static double[] redShortBackDropR = {22,-39,-90};

    public static double[] redLongFirstIntake = {57,0,-93};

    public static double[] redLongBackDropL = {0,0,0};
    public static double[] redLongBackDropM = {32,-91,-90};
    public static double[] redLongBackDropR = {0,0,0};

    public static double[] blueShortBackDropL = {0,0,0};
    public static double[] blueShortBackDropM = {0,0,0};
    public static double[] blueShortBackDropR = {0,0,0};

    public static double[] blueLongBackDropL = {0,0,0};
    public static double[] blueLongBackDropM = {0,0,0};
    public static double[] blueLongBackDropR = {0,0,0};

    public static double[] redShortCycleScore = {37.5,-38.5,-90};

    public static double[] redShortExtending1 = {54,-30,-90};
    public static double[] redShortExtending2 = {54,31,-87};

    public static double[] redLongExtending1 = {54,-91,-90};
    public static double[] redLongExtending2 = {54,5,-90};

    //-------------------------------------------------------------

    public Pose2d poseStartPose = arrayToPose(startPose);

    public Pose2d poseRedShortCycleScore = arrayToPose(redShortCycleScore);

    public Pose2d poseRedShortSpikeMarkL = arrayToPose(redShortSpikeMarkL);
    public Pose2d poseRedShortSpikeMarkM = arrayToPose(redShortSpikeMarkM);
    public Pose2d poseRedShortSpikeMarkR = arrayToPose(redShortSpikeMarkR);

    public Pose2d poseRedLongSpikeMarkL = arrayToPose(redLongSpikeMarkL);
    public Pose2d poseRedLongSpikeMarkM = arrayToPose(redLongSpikeMarkM);
    public Pose2d poseRedLongSpikeMarkR = arrayToPose(redLongSpikeMarkR);

    public Pose2d poseRedLongFirstIntake = arrayToPose(redLongFirstIntake);

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

    public Pose2d posePreBackDropBlueLong = arrayToPose(preBackDropBlueLong);
    public Pose2d posePreBackDropRedLong = arrayToPose(preBackDropRedLong);

    public Pose2d poseRedShortExtending1 = arrayToPose(redShortExtending1);
    public Pose2d poseRedShortExtending2 = arrayToPose(redShortExtending2);

    public Pose2d poseRedLongExtending1 = arrayToPose(redLongExtending1);
    public Pose2d poseRedLongExtending2 = arrayToPose(redLongExtending2);





}
