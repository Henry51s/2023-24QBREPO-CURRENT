package org.firstinspires.ftc.teamcode.Opmodes.auto.Bases;

import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.PARK;
import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.TRANSFER;
import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.TRANSFERMORE;
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
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.CommandType;
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

public abstract class ShortSideBaseOpMode extends OpMode {


    protected AutoStages autoState = AutoStages.SPIKE_MARK;
    protected Commands commands = new Commands();
    protected Hardware hardware = new Hardware();

    protected Autonomous auto;
    protected Intake intake;
    protected Extension extension;
    protected SampleMecanumDrive drive;
    protected Webcam webcam = new Webcam();
    protected AutoLocation autoLocation;

    protected TrajectorySequence scoreSpikeMark, scoreBackDrop, toExtend, extending, cycleToExtend, cycleToExtending, extendToBackDrop;
    protected ElapsedTime timer = new ElapsedTime();
    protected int cycleCounter = 0;


    public void init(AutoLocation autoLocation, PrimaryDetectionPipeline.Color color) {
        this.autoLocation = autoLocation;
        hardware.initAuto(hardwareMap);
        intake = hardware.intakeInstance;
        extension = hardware.extensionInstance;
        extension.startLoopExtensionAutoAsync();

        commands.initCommands(telemetry);
        commands.toInit(true);
        intake.setIntakeArmState(Intake.IntakeArmState.GROUND);
        extension.setExtensionState(RETRACTED);
        webcam.initCamera(hardwareMap, color);

        auto = new Autonomous(hardwareMap);
        drive = auto.getDrive();

    }
    @Override
    public void init_loop(){
        telemetry.addData("Location: ", webcam.getLocation());
        telemetry.update();
        if(webcam.getLocation() == PrimaryDetectionPipeline.ItemLocation.CENTER){
            auto.setPath(autoLocation, SpikeMark.MIDDLE);
        }
        else if(webcam.getLocation() == PrimaryDetectionPipeline.ItemLocation.RIGHT){
            auto.setPath(autoLocation, SpikeMark.RIGHT);
        }
        else if(webcam.getLocation() == PrimaryDetectionPipeline.ItemLocation.LEFT){
            auto.setPath(autoLocation, SpikeMark.LEFT);
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
                intake.runIntakeSetTime(1000, Intake.IntakeState.NORMAL);
                intake.runIntakeSetTime(1000, Intake.IntakeState.REVERSED);
                autoState = AutoStages.RETRACT;
                break;
            case RETRACT:
                drive.followTrajectorySequenceAsync(extendToBackDrop);
                extension.setExtensionState(Extension.ExtensionState.RETRACTED);
                intake.runIntakeSetTimeAsync(3000, Intake.IntakeState.NORMAL);
                while(drive.isBusy()){
                    drive.update();
                    if(Math.abs(extension.getAveragePosition() - extension.getTargetPosition()) < 5){
                        autoState = AutoStages.TRANSFER;
                    }
                }
                autoState = TRANSFER;
                break;
            case TRANSFER:
                commands.runFullSequence(CommandType.NORMAL);
                autoState = TRANSFERMORE;
                break;

            case TRANSFERMORE:

                commands.extendLift(Lift.LiftState.AUTO_LOW);
                while(Math.abs(commands.getLiftPosition() - LIFT_AUTO_LOW) < 5){

                }
                autoState = DEPOSIT;
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
        telemetry.addData("Pose: ", drive.getPoseEstimate());
        telemetry.addData("Extension Target: ", extension.getTargetPosition());
        telemetry.addData("Extension Average Position: ", extension.getAveragePosition());
        telemetry.addData("Intake Arm State: ", intake.getIntakeArmState());
        telemetry.addData("FourBar State: ", commands.getFourBarState());
        telemetry.addData("Differential State: ", commands.getDifferentialState());
        telemetry.addData("Claw State: ", commands.getClawState());
        telemetry.addData("Stage: ", autoState);

    }

    @Override
    public void stop(){
        extension.stopLoopExtensionAutoAsync();
        commands.extendLift(Lift.LiftState.RETRACTED);
        commands.toInit(false);
    }
}
