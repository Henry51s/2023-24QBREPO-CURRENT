package org.firstinspires.ftc.teamcode.Opmodes.auto;

import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.PARK;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.LIFT_AUTO_LOW;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.MAX_CYCLES;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension.ExtensionState.RETRACTED;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake.IntakeArmState.FIFTH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake.IntakeArmState.GROUND;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake.IntakeArmState.SECOND;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoLocation;
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages;
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.SpikeMark;
import org.firstinspires.ftc.teamcode.NonOpmodes.Pathing.Autonomous;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Commands;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.Webcam;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="RedShortIterative")
public class RedShortIterative extends OpMode {


    AutoStages autoState = AutoStages.SPIKE_MARK;

    Autonomous auto;
    Commands commands = new Commands();
    Intake intake;
    Extension extension;
    Hardware hardware = new Hardware();
    SampleMecanumDrive drive;

    TrajectorySequence scoreSpikeMark, scoreBackDrop, toExtend, extending, cycleToExtend, cycleToExtending, extendToBackDrop;
    Webcam webcam = new Webcam();
    ElapsedTime timer = new ElapsedTime();

    int cycleCounter = 0;
    @Override
    public void init() {
        hardware.initAuto(hardwareMap);
        intake = hardware.intakeInstance;
        extension = hardware.extensionInstance;
        extension.startLoopExtensionAutoAsync();

        commands.initCommands(telemetry);
        commands.toInit(true);
        intake.setIntakeArmState(Intake.IntakeArmState.GROUND);
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

        scoreSpikeMark = auto.scoreSpikeMark;
        scoreBackDrop = auto.scoreBackDrop;
        toExtend = auto.toExtend;
        cycleToExtend = auto.cycleToExtend;
        extending = auto.extending;
        cycleToExtending = auto.cycleToExtending;
        extendToBackDrop = auto.extendToBackDrop;

    }

    @Override
    public void loop() {
        cycleCounter = Math.max(0, Math.min(cycleCounter, MAX_CYCLES));
        switch (autoState){


            case SPIKE_MARK:
                intake.setIntakeArmState(GROUND);
                drive.followTrajectorySequence(scoreSpikeMark);
                intake.setIntakeArmState(FIFTH);
                autoState = AutoStages.PIXEL_DEPOSIT;
                break;
            case PIXEL_DEPOSIT:
                commands.extendLift(Lift.LiftState.AUTO_LOW);
                commands.toDeposit();
                drive.followTrajectorySequence(scoreBackDrop);
                commands.releasePixels();
                commands.extendLift(Lift.LiftState.RETRACTED);
                drive.followTrajectorySequence(toExtend);
                //extension.setExtensionState(Extension.ExtensionState.FAR);
                drive.followTrajectorySequence(extending);
                autoState = AutoStages.INITIAL_EXTEND;
                break;
            case INITIAL_EXTEND:
                extension.setExtensionState(Extension.ExtensionState.FAR);
                if(Math.abs(extension.getAveragePosition() - extension.getTargetPosition()) < 10){
                    autoState = AutoStages.INTAKE;
                }
                break;
            case CYCLE_EXTEND:
                extension.setExtensionState(Extension.ExtensionState.FAR);
                if(cycleCounter == 1){
                    intake.setIntakeArmState(SECOND);
                }
                if(Math.abs(extension.getAveragePosition() - extension.getTargetPosition()) < 10){
                    autoState = AutoStages.INTAKE;
                }
                break;

            case INTAKE:
                intake.runIntakeSetTime(500, Intake.IntakeState.NORMAL);
                intake.runIntakeSetTime(100, Intake.IntakeState.REVERSED);
                autoState = AutoStages.RETRACT;
                break;
            case RETRACT:
                extension.setExtensionState(Extension.ExtensionState.RETRACTED);
                intake.runIntakeSetTimeAsync(5000, Intake.IntakeState.NORMAL);
                
                if(Math.abs(extension.getAveragePosition() - extension.getTargetPosition()) < 5){
                    autoState = AutoStages.TRANSFER;
                }
                break;
            case TRANSFER:
                commands.runFullSequence();
                commands.extendLift(Lift.LiftState.AUTO_LOW);
                if(Math.abs(commands.getLiftPosition() - LIFT_AUTO_LOW) < 10){
                    autoState = AutoStages.DEPOSIT;
                }
                break;
            case DEPOSIT:
                commands.releasePixelsToIntermediate();
                commands.extendLift(Lift.LiftState.RETRACTED);
                cycleCounter ++;
                if(cycleCounter <= MAX_CYCLES){
                    drive.followTrajectorySequence(cycleToExtend);
                    autoState = AutoStages.CYCLE_EXTEND;
                }
                else {
                    autoState = PARK;
                }
                break;
            case PARK:
                break;
        }
        telemetry.addData("Extension Target: ", extension.getTargetPosition());
        telemetry.addData("Extension Average Position: ", extension.getAveragePosition());
    }
    @Override
    public void stop(){
        extension.stopLoopExtenstionAutoAsync();
    }
}
