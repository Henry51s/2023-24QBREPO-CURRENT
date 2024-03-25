package org.firstinspires.ftc.teamcode.Opmodes.auto.Bases;

import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.CYCLE_EXTEND;
import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.INTAKE;
import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.PARK;
import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.PIXEL_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.RETRACT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.TO_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.OpModeType.AUTONOMOUS;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.AUTO_INTAKE_TIME;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.LIFT_AUTO_LOW;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension.ExtensionState.FAR;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension.ExtensionState.LONG_SIDE_AUTO_INTAKE;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension.ExtensionState.RETRACTED;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake.IntakeArmState.FIFTH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake.IntakeArmState.GROUND;


import com.acmerobotics.dashboard.config.Config;
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
import org.firstinspires.ftc.teamcode.Opmodes.auto.BlueLong;

@Config
public abstract class LongAutoBase extends OpMode {



    public static AutoLocation autoLocationDebug = AutoLocation.RED_LONG;
    public static SpikeMark spikeMarkDebug = SpikeMark.LEFT;

    protected AutoStages autoState = AutoStages.SPIKE_MARK;
    protected Commands commands = new Commands();
    protected Hardware hardware = new Hardware();

    protected Autonomous auto;
    protected Intake intake;
    protected Extension extension;
    protected SampleMecanumDrive drive;
    protected Webcam webcam = new Webcam();
    protected AutoLocation autoLocation;

    protected TrajectorySequence scoreSpikeMark, scoreBackDrop, toExtend, extending, cycleToExtend, cycleToExtending, extendToBackDrop, firstIntake, parkLeft;
    protected ElapsedTime timer = new ElapsedTime();
    protected int cycleCounter = 0;
    protected int maxCycles = 0;


    public void init(AutoLocation autoLocation, PrimaryDetectionPipeline.Color color, int maxCycles) {
        this.autoLocation = autoLocation;
        this.maxCycles = maxCycles;
        hardware.initAuto(hardwareMap);
        intake = hardware.intakeInstance;

        commands.initCommands();
        commands.toInit(true);

        extension = hardware.extensionInstance;
        extension.startLoopExtensionAutoAsync();
        //extension.homeInAuto();



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
        //parkLeft = auto.parkLeft;
        firstIntake = auto.firstIntake;
    }

    @Override
    public void loop() {
        cycleCounter = Math.max(0, Math.min(cycleCounter, maxCycles));
        switch (autoState){
            case SPIKE_MARK:
                commands.toIntermediate(AUTONOMOUS,CommandType.ASYNC);
                intake.setIntakeArmState(GROUND);
                drive.followTrajectorySequence(scoreSpikeMark);
                intake.setIntakeArmState(FIFTH);
                //autoState = AutoStages.LONG_FIRST_INTAKE;
                //autoState = TO_DEPOSIT;
                autoState = PARK;

                break;
            case TO_DEPOSIT:
                commands.toDeposit(AUTONOMOUS,CommandType.ASYNC);
                drive.followTrajectorySequence(scoreBackDrop);
                commands.extendLift(Lift.LiftState.MED);
                while(Math.abs(commands.lift.getCurrentPosition() - commands.lift.getTargetPosition()) > 10){}
                commands.releasePixelsToIntermediate(AUTONOMOUS, CommandType.ASYNC);
                commands.extendLift(Lift.LiftState.RETRACTED);
                autoState = PARK;
                break;
            case LONG_FIRST_INTAKE:
                drive.followTrajectorySequence(firstIntake);
                extension.setExtensionState(LONG_SIDE_AUTO_INTAKE);

                while(Math.abs(extension.getAveragePosition() - extension.getTargetPosition()) > 10){
                }
                intake.runIntakeSetTime(AUTO_INTAKE_TIME, Intake.IntakeState.NORMAL);
                intake.runIntakeSetTime(AUTO_INTAKE_TIME/2, Intake.IntakeState.REVERSED);

                autoState = RETRACT;
                break;

            case RETRACT:
                intake.runIntakeSetTimeAsync(2000, Intake.IntakeState.NORMAL);
                extension.setExtensionState(RETRACTED);
                while(Math.abs(extension.getAveragePosition() - extension.getTargetPosition()) > 10){
                }
                commands.toPickup(AUTONOMOUS, CommandType.BLOCKING);
                commands.toDeposit(AUTONOMOUS, CommandType.ASYNC);
                drive.followTrajectorySequence(scoreBackDrop);
                /*timer.reset();
                while(timer.milliseconds() < 500){

                }*/

                autoState = PIXEL_DEPOSIT;
                break;


            case PIXEL_DEPOSIT:
                commands.extendLift(Lift.LiftState.AUTO_LOW);
                while(Math.abs(commands.getLiftPosition() - LIFT_AUTO_LOW) > 5){

                }
                commands.releasePixelsToIntermediate(AUTONOMOUS, CommandType.ASYNC);
                commands.extendLift(Lift.LiftState.RETRACTED);


                autoState = CYCLE_EXTEND;
                break;

            case CYCLE_EXTEND:
                //drive to extending position
                drive.followTrajectorySequence(cycleToExtend);
                extension.setExtensionState(FAR);
                while(Math.abs(extension.getAveragePosition() - extension.getTargetPosition()) > 5){

                }
                autoState = INTAKE;
                break;

            case INTAKE:
                intake.runIntakeSetTime(1000, Intake.IntakeState.NORMAL);
                intake.runIntakeSetTime(250, Intake.IntakeState.REVERSED_HALF);
                autoState = PARK;
                break;


            case TRANSFERMORE:

                break;

            case DEPOSIT:

                break;
            case PARK:


                break;
        }
        telemetry.addData("Pose: ", drive.getPoseEstimate());
        telemetry.addData("Extension Target: ", extension.getTargetPosition());
        telemetry.addData("Extension Average Position: ", extension.getAveragePosition());
        telemetry.addData("Intake Arm State: ", intake.getIntakeArmState());
        telemetry.addData("FourBar State: ", commands.fourBar.getState());
        telemetry.addData("Differential State: ", commands.differential.getState());
        telemetry.addData("Claw State: ", commands.claw.getClawState());
        telemetry.addData("Lift State: ", commands.lift.getLiftState());
        telemetry.addData("Stage: ", autoState);

    }

    @Override
    public void stop(){
        extension.stopLoopExtensionAutoAsync();
        commands.extendLift(Lift.LiftState.RETRACTED);
        //commands.toInit(false);
    }
}
