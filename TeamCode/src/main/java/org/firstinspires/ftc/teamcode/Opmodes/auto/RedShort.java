package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Commands;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.Webcam;
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoLocation;
import org.firstinspires.ftc.teamcode.NonOpmodes.Pathing.Autonomous;
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.SpikeMark;

@Config
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="RedShort")
public class RedShort extends LinearOpMode {
    Autonomous autonomous;
    SampleMecanumDrive drive;
    Commands commands = new Commands();

    Hardware hw = new Hardware();

    Intake intake;
    Extension extendo;

    Webcam webcam = new Webcam();

    public TrajectorySequence scoreSpikeMark, scoreBackDrop, park;


    @Override
    public void runOpMode() throws InterruptedException {


        hw.initAuto(hardwareMap);
        intake = hw.intakeInstance;
        extendo = hw.extensionInstance;

        commands.initCommands(telemetry);
        commands.toInit();
        intake.setIntakeArmState(Intake.IntakeArmState.INIT);
        extendo.setExtensionState(Extension.ExtensionState.RETRACTED);

        webcam.initCamera(hardwareMap, PrimaryDetectionPipeline.Color.RED);
        autonomous = new Autonomous(hardwareMap);
        drive = autonomous.getDrive();

        while(opModeInInit()){
            telemetry.addData("Location: ", webcam.getLocation());
            telemetry.update();
            if(webcam.getLocation() == PrimaryDetectionPipeline.ItemLocation.CENTER){
                autonomous.setPath(AutoLocation.RED_SHORT, SpikeMark.MIDDLE);
            }
            else if(webcam.getLocation() == PrimaryDetectionPipeline.ItemLocation.RIGHT){
                autonomous.setPath(AutoLocation.RED_SHORT, SpikeMark.RIGHT);
            }
            else if(webcam.getLocation() == PrimaryDetectionPipeline.ItemLocation.LEFT){
                autonomous.setPath(AutoLocation.RED_SHORT, SpikeMark.LEFT);
            }
        }



        waitForStart();
        if(isStopRequested()){
            commands.extendLift(Lift.LiftState.RETRACTED);
            return;
        }
        webcam.stopStreaming();

        scoreSpikeMark = autonomous.scoreSpikeMark;
        scoreBackDrop = autonomous.scoreBackDrop;
        park = autonomous.park;

        intake.setIntakeArmState(Intake.IntakeArmState.SPIKEMARK);

        drive.followTrajectorySequence(scoreSpikeMark);
        //commands.toDeposit();
        //intake.runIntakeSetTime(1, true,power);
        //intake.setIntakeArmState(Intake.IntakeArmState.FIFTH);

        //commands.extendLift(Lift.LiftState.LOW);
        //drive.followTrajectorySequence(scoreBackDrop);
        //commands.releasePixels();
        //drive.followTrajectorySequence(park);
        //commands.extendLift(Lift.LiftState.RETRACTED);



        //drive.followTrajectorySequence(park);



        while(opModeIsActive()){

        }
    }
}
