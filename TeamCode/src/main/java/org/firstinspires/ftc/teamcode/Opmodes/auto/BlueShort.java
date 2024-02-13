package org.firstinspires.ftc.teamcode.Opmodes.auto;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline.ItemLocation.CENTER;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline.ItemLocation.RIGHT;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Commands;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.Webcam;
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoLocation;
import org.firstinspires.ftc.teamcode.NonOpmodes.Pathing.Autonomous;
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.SpikeMark;

@Disabled
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="BlueShort")
public class BlueShort extends LinearOpMode {

    Hardware hardware = new Hardware();
    Intake intake;
    Extension extendo;

    Commands commands = new Commands();
    Webcam webcam = new Webcam();


    Autonomous autonomous;
    SampleMecanumDrive drive;
    TrajectorySequence scoreSpikeMark, scoreBackBoard, park;


    @Override
    public void runOpMode() throws InterruptedException {
        autonomous = new Autonomous(hardwareMap);
        drive = autonomous.getDrive();


        hardware.initAuto(hardwareMap);
        commands.initCommands();
        commands.toInit(true);

        intake = hardware.intakeInstance;
        extendo = hardware.extensionInstance;
        intake.setIntakeArmState(Intake.IntakeArmState.INIT);


        webcam.initCamera(hardwareMap, PrimaryDetectionPipeline.Color.BLUE);

        while(opModeInInit()){
            telemetry.addData("Location: ", webcam.getLocation());
            telemetry.update();
            if(webcam.getLocation() == PrimaryDetectionPipeline.ItemLocation.LEFT){
                autonomous.setPath(AutoLocation.BLUE_SHORT, SpikeMark.LEFT);
            }
            else if(webcam.getLocation() == CENTER){
                autonomous.setPath(AutoLocation.BLUE_SHORT, SpikeMark.MIDDLE);
            }
            else if(webcam.getLocation() == RIGHT){
                autonomous.setPath(AutoLocation.BLUE_SHORT, SpikeMark.RIGHT);
            }
            else{
                autonomous.setPath(AutoLocation.BLUE_SHORT, SpikeMark.MIDDLE);
            }

        }
        scoreSpikeMark = autonomous.scoreSpikeMark;
        scoreBackBoard = autonomous.scoreBackDrop;
        park = autonomous.park;


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
