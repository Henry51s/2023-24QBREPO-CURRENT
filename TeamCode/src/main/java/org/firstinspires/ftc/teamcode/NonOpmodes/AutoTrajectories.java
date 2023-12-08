package org.firstinspires.ftc.teamcode.NonOpmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;

import java.util.Vector;

public class AutoTrajectories {

    SampleMecanumDrive drive;
    public Pose2d startPose = new Pose2d(0,0,0);

    public Pose2d preScore;
    public Pose2d score;
    public Trajectory toScore;

    public AutoTrajectories(HardwareMap hw){
        drive = new SampleMecanumDrive(hw);
        drive.setPoseEstimate(startPose);
    }
    public void BlueLeft(){
        toScore = drive.trajectoryBuilder(startPose)
                .splineToConstantHeading(new Vector2d(45,-27), Math.toRadians(0))
                .build();
    }
}
