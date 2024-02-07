package org.firstinspires.ftc.teamcode.Opmodes.auto;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension.ExtensionState.RETRACTED;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake.IntakeArmState.FIFTH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake.IntakeArmState.GROUND;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoLocation;
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.SpikeMark;
import org.firstinspires.ftc.teamcode.NonOpmodes.Pathing.Autonomous;
import org.firstinspires.ftc.teamcode.NonOpmodes.Pathing.PointsOfInterest;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Commands;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.Webcam;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="RedShortIterative")
public class RedShortITerative extends OpMode {


    public enum AutoState{
        AUTO_INITIALIZE,
        AUTO_SPIKEMARK,
        AUTO_PIXELDEPOSIT,
        AUTO_EXTEND,
        AUTO_INTAKE,
        AUTO_RETRACT,
        AUTO_TRANSFER,
        AUTO_DEPOSIT

    }

    AutoState autoState = AutoState.AUTO_SPIKEMARK;

    Autonomous auto;
    Commands commands = new Commands();
    Intake intake;
    Extension extension;
    Hardware hardware = new Hardware();
    SampleMecanumDrive drive;

    TrajectorySequence scoreSpikeMark, scoreBackDrop, toExtend, extendToBackDrop;
    Webcam webcam = new Webcam();
    @Override
    public void init() {
        hardware.initAuto(hardwareMap);
        intake = hardware.intakeInstance;
        extension = hardware.extensionInstance;

        commands.initCommands(telemetry);
        commands.toInit();
        intake.setIntakeArmState(Intake.IntakeArmState.INIT);
        extension.setExtensionState(RETRACTED);
        webcam.initCamera(hardwareMap, PrimaryDetectionPipeline.Color.RED);

        auto = new Autonomous(hardwareMap);
        drive = auto.getDrive();
    }
    @Override
    public void init_loop(){
        telemetry.addData("Location: ", webcam.getLocation());
        telemetry.update();
        if(webcam.getLocation() == PrimaryDetectionPipeline.ItemLocation.CENTER){
            auto.setPath(AutoLocation.RED_SHORT, SpikeMark.MIDDLE);
        }
        else if(webcam.getLocation() == PrimaryDetectionPipeline.ItemLocation.RIGHT){
            auto.setPath(AutoLocation.RED_SHORT, SpikeMark.RIGHT);
        }
        else if(webcam.getLocation() == PrimaryDetectionPipeline.ItemLocation.LEFT){
            auto.setPath(AutoLocation.RED_SHORT, SpikeMark.LEFT);
        }

        extension.loopExtensionAuto();

        scoreSpikeMark = auto.scoreSpikeMark;
        scoreBackDrop = auto.scoreBackDrop;
        toExtend = auto.toExtend;
        extendToBackDrop = auto.extendToBackDrop;

    }

    @Override
    public void loop() {
        extension.loopExtensionAuto();
        switch (autoState){


            case AUTO_SPIKEMARK:
                intake.setIntakeArmState(GROUND);
                drive.followTrajectorySequence(scoreSpikeMark);
                intake.setIntakeArmState(FIFTH);
                autoState = AutoState.AUTO_PIXELDEPOSIT;
                break;
            case AUTO_PIXELDEPOSIT:
                commands.toDeposit();
                drive.followTrajectorySequence(scoreBackDrop);
                commands.releasePixels();
                drive.followTrajectorySequence(toExtend);
                autoState = AutoState.AUTO_EXTEND;
                break;
            case AUTO_EXTEND:
                extension.setExtensionState(Extension.ExtensionState.FAR);
                if(Math.abs(extension.getAveragePosition() - extension.getTargetPosition()) < 5){
                    autoState = AutoState.AUTO_INTAKE;
                }
                break;
            case AUTO_INTAKE:
                intake.runIntakeSetTime(1000);
                autoState = AutoState.AUTO_RETRACT;
                break;
            case AUTO_RETRACT:
                extension.setExtensionState(RETRACTED);
                if(Math.abs(extension.getAveragePosition() - extension.getTargetPosition()) < 5){
                    autoState = AutoState.AUTO_TRANSFER;
                }
                break;
            case AUTO_TRANSFER:
                drive.followTrajectorySequence(extendToBackDrop);
                //drive to backboard (async?)
                //commands.toPickup();
                //commands.toDeposit();
                break;
            case AUTO_DEPOSIT:
                break;
        }
    }
}
