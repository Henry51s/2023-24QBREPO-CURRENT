package org.firstinspires.ftc.teamcode.Opmodes.auto.Pathing;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;


@Config
public class PointsOfInterest {


    private Pose2d arrayToPose(double[] coordinates){
        Pose2d resultPose = new Pose2d(coordinates[0], coordinates[1], Math.toRadians(coordinates[2]));
        return resultPose;
    }

    public static double[] redShortStart = {16,-61,-90};
    public static double[] redShortSpikeMarkL = {20,-30,-90};
    public static double[] redShortSpikeMarkM = {12,-32,-90};
    public static double[] redShortSpikeMarkR = {28,-40,0};




    public static double[] redLongStart = {-37.5,-61,-90};
    public static double[] redLongSpikeMarkL = {-47,-42,-90};
    public static double[] redLongSpikeMarkM = {-37.5,-33,-90};
    public static double[] redLongSpikeMarkR = {-34.8,-29,-90};
    public static double[] preBackDropRedLong = {-37.5,-57,90};



    public static double[] blueShortStart = {16,61,90};
    public static double[] blueShortSpikeMarkL = {28,40,90};
    public static double[] blueShortSpikeMarkM = {12,32,90};
    public static double[] blueShortSpikeMarkR = {20,30,0};



    public static double[] blueLongStart = {-37.5,61,90};
    public static double[] blueLongSpikeMarkL = {-36.8,29,90};
    public static double[] blueLongSpikeMarkM = {-37.5,33,90};
    public static double[] blueLongSpikeMarkR = {-47,42,90};
    public static double[] preBackDropBlueLong = {0,0,0};



    public static double[] redBackDropL = {0,0,0};
    public static double[] redBackDropM = {0,0,0};
    public static double[] redBackDropR = {0,0,0};

    public static double[] blueBackDropL = {0,0,0};
    public static double[] blueBackDropM = {0,0,0};
    public static double[] blueBackDropR = {0,0,0};

    //-------------------------------------------------------------

    public Pose2d poseRedShortStart = arrayToPose(redShortStart);
    public Pose2d poseRedLongStart = arrayToPose(redLongStart);
    public Pose2d poseBlueShortStart = arrayToPose(blueShortStart);
    public Pose2d poseBlueLongStart = arrayToPose(blueLongStart);

    public Pose2d poseRedShortSpikeMarkL = arrayToPose(redShortSpikeMarkL);
    public Pose2d poseRedShortSpikeMarkM = arrayToPose(redShortSpikeMarkM);
    public Pose2d poseRedShortSpikeMarkR = arrayToPose(redShortSpikeMarkR);

    public Pose2d poseRedLongSpikeMarkL = arrayToPose(redLongSpikeMarkL);
    public Pose2d poseRedLongSpikeMarkM = arrayToPose(redLongSpikeMarkM);
    public Pose2d poseRedLongSpikeMarkR = arrayToPose(redLongSpikeMarkR);

    public Pose2d poseBlueShortSpikeMarkL = arrayToPose(blueShortSpikeMarkL);
    public Pose2d poseBlueShortSpikeMarkM = arrayToPose(blueShortSpikeMarkM);
    public Pose2d poseBlueShortSpikeMarkR = arrayToPose(blueShortSpikeMarkR);

    public Pose2d poseBlueLongSpikeMarkL = arrayToPose(blueLongSpikeMarkL);
    public Pose2d poseBlueLongSpikeMarkM = arrayToPose(blueLongSpikeMarkM);
    public Pose2d poseBlueLongSpikeMarkR = arrayToPose(blueLongSpikeMarkR);

    public Pose2d poseRedBackDropL = arrayToPose(redBackDropL);
    public Pose2d poseRedBackDropM = arrayToPose(redBackDropM);
    public Pose2d poseRedBackDropR = arrayToPose(redBackDropR);

    public Pose2d poseBlueBackDropL = arrayToPose(blueBackDropL);
    public Pose2d poseBlueBackDropM = arrayToPose(blueBackDropM);
    public Pose2d poseBlueBackDropR = arrayToPose(blueBackDropR);

    public Pose2d posePreBackDropBlueLong = arrayToPose(preBackDropBlueLong);
    public Pose2d posePreBackDropRedLong = arrayToPose(preBackDropRedLong);





}
