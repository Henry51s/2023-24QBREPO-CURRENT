package org.firstinspires.ftc.teamcode.Opmodes.auto;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline.ItemLocation.CENTER;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline.ItemLocation.RIGHT;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Commands;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.FourBar;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.Webcam;

@Autonomous(name="BlueShort")
public class BlueShort extends LinearOpMode {

    Hardware hardware = new Hardware();
    Intake intake;
    Extension extendo;

    Commands commands = new Commands();
    Webcam webcam = new Webcam();


    AutoTrajectories autoTrajectories;
    SampleMecanumDrive drive;
    TrajectorySequence scoreSpikeMark, scoreBackBoard, park;


    @Override
    public void runOpMode() throws InterruptedException {
        autoTrajectories = new AutoTrajectories(hardwareMap);
        drive = autoTrajectories.drive;


        hardware.initAuto(hardwareMap);
        commands.initCommands(telemetry);
        commands.toInit();

        intake = hardware.intakeInstance;
        extendo = hardware.extensionInstance;
        intake.setIntakeArmState(Intake.IntakeArmState.INIT);


        webcam.initCamera(hardwareMap, PrimaryDetectionPipeline.Color.BLUE);

        while(opModeInInit()){
            telemetry.addData("Location: ", webcam.getLocation());
            telemetry.update();
            if(webcam.getLocation() == PrimaryDetectionPipeline.ItemLocation.LEFT){
                autoTrajectories.setPath(AutoTrajectories.AutoLocation.BLUE_SHORT, AutoTrajectories.SpikeMark.LEFT);
            }
            else if(webcam.getLocation() == CENTER){
                autoTrajectories.setPath(AutoTrajectories.AutoLocation.BLUE_SHORT, AutoTrajectories.SpikeMark.MIDDLE);
            }
            else if(webcam.getLocation() == RIGHT){
                autoTrajectories.setPath(AutoTrajectories.AutoLocation.BLUE_SHORT, AutoTrajectories.SpikeMark.RIGHT);
            }
            else{
                autoTrajectories.setPath(AutoTrajectories.AutoLocation.BLUE_SHORT, AutoTrajectories.SpikeMark.MIDDLE);
            }

        }
        scoreSpikeMark = autoTrajectories.scoreSpikeMark;
        scoreBackBoard = autoTrajectories.scoreBackDrop;
        park = autoTrajectories.park;


        waitForStart();
        if(isStopRequested()){
            commands.extendLift(Lift.LiftState.RETRACTED);
            return;
        }
        webcam.stopStreaming();
        intake.setIntakeArmState(Intake.IntakeArmState.SPIKEMARK);
        drive.followTrajectorySequence(scoreSpikeMark);
        intake.setIntakeArmState(Intake.IntakeArmState.FIFTH);
        commands.extendLift(Lift.LiftState.LOW);
        commands.toDeposit();

        drive.followTrajectorySequence(scoreBackBoard);
        commands.releasePixels();








        while(opModeIsActive()){


        }


    }


}
