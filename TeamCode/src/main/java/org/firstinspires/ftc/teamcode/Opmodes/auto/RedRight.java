package org.firstinspires.ftc.teamcode.Opmodes.auto;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.FOURBAR_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.FOURBAR_INIT;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.FourBar;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.Webcam;

@Autonomous(name="RedRight")
public class RedRight extends LinearOpMode {
    AutoTrajectories autoTrajectories;
    TrajectorySequence scoreSpikeMark, scoreBackBoard, park, backBoardOffset;
    SampleMecanumDrive drive;

    Hardware hw = new Hardware();

    Intake intake;
    FourBar fourBar;
    Differential diff;
    Claw claw;

    Webcam webcam;

    @Override
    public void runOpMode() throws InterruptedException {
        autoTrajectories = new AutoTrajectories(hardwareMap);
        autoTrajectories.setPath(AutoTrajectories.AutoLocation.RED_RIGHT, autoTrajectories.spikeMark);
        drive = autoTrajectories.drive;
        scoreSpikeMark = autoTrajectories.scoreSpikeMark;
        scoreBackBoard = autoTrajectories.scoreBackBoard;

        hw.initAuto(hardwareMap);
        intake = hw.intakeInstance;
        fourBar = hw.fourBarInstance;
        diff = hw.differentialInstance;
        claw = hw.clawInstance;

        fourBar.setFourBarPosition(FOURBAR_INIT);
        diff.setDiffState(Differential.DiffState.DEPOSIT);
        claw.setClawState(Claw.ClawState.CLOSE_ONE_PIXEL);


        waitForStart();
        if(isStopRequested()){
            return;
        }

        drive.followTrajectorySequence(scoreSpikeMark);
        intake.runIntakeSetTime(500, true);
        fourBar.setFourBarState(FourBar.FourBarState.DEPOSIT);
        drive.followTrajectorySequence(scoreBackBoard);

        claw.setClawState(Claw.ClawState.OPEN);
        //drive.followTrajectorySequence(park);



        while(opModeIsActive()){

        }
    }
}
