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
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline;
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

    Webcam webcam = new Webcam();

    @Override
    public void runOpMode() throws InterruptedException {


        hw.initAuto(hardwareMap);
        intake = hw.intakeInstance;
        fourBar = hw.fourBarInstance;
        diff = hw.differentialInstance;
        claw = hw.clawInstance;


        webcam.initCamera(hardwareMap, PrimaryDetectionPipeline.Color.RED);



        fourBar.setFourBarPosition(FOURBAR_INIT);
        diff.setDiffState(Differential.DiffState.DEPOSIT);
        claw.setClawState(Claw.ClawState.CLOSE_ONE_PIXEL);

        autoTrajectories = new AutoTrajectories(hardwareMap);

        while(opModeInInit()){
            telemetry.addData("Location: ", webcam.getLocation());
            telemetry.update();
            if(webcam.getLocation() == PrimaryDetectionPipeline.ItemLocation.CENTER){
                autoTrajectories.setPath(AutoTrajectories.AutoLocation.RED_RIGHT, AutoTrajectories.SpikeMark.MIDDLE);
            }
            if(webcam.getLocation() == PrimaryDetectionPipeline.ItemLocation.RIGHT){
                autoTrajectories.setPath(AutoTrajectories.AutoLocation.RED_RIGHT, AutoTrajectories.SpikeMark.RIGHT);
            }
            if(webcam.getLocation() == PrimaryDetectionPipeline.ItemLocation.LEFT){
                autoTrajectories.setPath(AutoTrajectories.AutoLocation.RED_RIGHT, AutoTrajectories.SpikeMark.LEFT);
            }
        }

        drive = autoTrajectories.drive;
        scoreSpikeMark = autoTrajectories.scoreSpikeMark;
        scoreBackBoard = autoTrajectories.scoreBackBoard;


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
