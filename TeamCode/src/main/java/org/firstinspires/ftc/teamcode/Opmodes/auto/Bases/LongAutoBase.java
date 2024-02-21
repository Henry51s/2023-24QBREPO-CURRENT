package org.firstinspires.ftc.teamcode.Opmodes.auto.Bases;

import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.CYCLE_EXTEND;
import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.INTAKE;
import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.PARK;
import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.PIXEL_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoStages.RETRACT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.LIFT_AUTO_LOW;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.MAX_CYCLES;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension.ExtensionState.FAR;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension.ExtensionState.LONG_SIDE_AUTO_INTAKE;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension.ExtensionState.MED;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension.ExtensionState.RETRACTED;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension.ExtensionState.SHORT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake.IntakeArmState.FIFTH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake.IntakeArmState.GROUND;


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
import org.firstinspires.ftc.teamcode.Opmodes.auto.RedLongUsingBase;

public abstract class LongAutoBase extends OpMode {


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


    public void init(AutoLocation autoLocation, PrimaryDetectionPipeline.Color color) {
        this.autoLocation = autoLocation;
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
        auto.setPath(AutoLocation.RED_LONG, SpikeMark.MIDDLE);

        scoreSpikeMark = auto.scoreSpikeMark;
        scoreBackDrop = auto.scoreBackDrop;
        toExtend = auto.toExtend;
        //cycleToExtend = auto.cycleToExtend;
        //extending = auto.extending;
        //cycleToExtending = auto.cycleToExtending;
        extendToBackDrop = auto.extendToBackDrop;
        //parkLeft = auto.parkLeft;
        firstIntake = auto.firstIntake;
    }

    @Override
    public void loop() {
        cycleCounter = Math.max(0, Math.min(cycleCounter, MAX_CYCLES));
        switch (autoState){


            case SPIKE_MARK:
                commands.toIntermediate(CommandType.ASYNC);
                intake.setIntakeArmState(GROUND);
                drive.followTrajectorySequence(scoreSpikeMark);
                intake.setIntakeArmState(FIFTH);
                autoState = AutoStages.LONG_FIRST_INTAKE;
                break;
            case LONG_FIRST_INTAKE:
                drive.followTrajectorySequence(firstIntake);
                extension.setExtensionState(LONG_SIDE_AUTO_INTAKE);

                while(Math.abs(extension.getAveragePosition() - extension.getTargetPosition()) > 10){
                }
                intake.runIntakeSetTime(2000, Intake.IntakeState.NORMAL);
                intake.runIntakeSetTime(500, Intake.IntakeState.REVERSED);

                autoState = RETRACT;

                break;

            case RETRACT:
                intake.runIntakeSetTimeAsync(3000, Intake.IntakeState.NORMAL);
                extension.setExtensionState(RETRACTED);
                drive.followTrajectorySequence(scoreBackDrop);
                while(Math.abs(extension.getAveragePosition() - extension.getTargetPosition()) > 10){
                }
                commands.toPickup(CommandType.ASYNC);
                timer.reset();
                while(timer.milliseconds() < 500){

                }
                commands.toDeposit(CommandType.ASYNC);
                autoState = PIXEL_DEPOSIT;
                break;


            case PIXEL_DEPOSIT:
                commands.extendLift(Lift.LiftState.AUTO_LOW);
                while(Math.abs(commands.getLiftPosition() - LIFT_AUTO_LOW) > 5){

                }
                commands.releasePixelsToIntermediate(CommandType.ASYNC);
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
        telemetry.addData("FourBar State: ", commands.getFourBarState());
        telemetry.addData("Differential State: ", commands.getDifferentialState());
        telemetry.addData("Claw State: ", commands.getClawState());
        telemetry.addData("Lift State: ", commands.getLiftState());
        telemetry.addData("Stage: ", autoState);

    }

    @Override
    public void stop(){
        extension.stopLoopExtensionAutoAsync();
        commands.extendLift(Lift.LiftState.RETRACTED);
        commands.toInit(false);
    }
}
