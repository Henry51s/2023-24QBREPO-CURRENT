package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonOpmodes.AutoTrajectories;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Diff;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.FourBar;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@Autonomous(name="BlueLeft")
public class BlueLeft extends LinearOpMode {

    Hardware hardware = new Hardware();
    Claw claw;
    Diff diff;
    FourBar fourBar;
    Lift lift;

    AutoTrajectories autoTrajectories;
    Trajectory toScore;


    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        //AutoTrajectories autoTrajectories;
        hardware.initRobot(hardwareMap);
        claw = new Claw(hardwareMap);
        diff = new Diff(hardwareMap);
        fourBar = new FourBar(hardwareMap);
        lift = new Lift(hardwareMap);

        claw.setClawState(Claw.ClawState.CLOSE);
        diff.setDiffState(Diff.DiffState.DEPOSIT);
        fourBar.setV4bState(FourBar.V4bState.INIT);
        lift.setLiftState(Lift.LiftState.RETRACTED);

        autoTrajectories = new AutoTrajectories(hardwareMap);
        autoTrajectories.BlueLeft();
        toScore = autoTrajectories.toScore;

        waitForStart();
        if(isStopRequested()){
            return;
        }

        drive.followTrajectory(toScore);
        fourBar.setV4bState(FourBar.V4bState.DEPOSIT);
        claw.setClawState(Claw.ClawState.OPEN);



        while(opModeIsActive()){
            telemetry.addData("Pose", drive.getPoseEstimate());
        }


    }


}
