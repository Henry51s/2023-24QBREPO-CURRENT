package org.firstinspires.ftc.teamcode.NonOpmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Opmodes.auto.BlueTopleft;

public class AutoTrajectories {
    SampleMecanumDrive drive;

    public Pose2d startPose = new Pose2d(0,0,0);
    public Trajectory test;
    public AutoTrajectories(HardwareMap hw){
        drive = new SampleMecanumDrive(hw);
        drive.setPoseEstimate(startPose);
    }

    public void BlueTopleft(){
        test = drive.trajectoryBuilder(new Pose2d())
                .forward(40)
                .build();

    }

}
