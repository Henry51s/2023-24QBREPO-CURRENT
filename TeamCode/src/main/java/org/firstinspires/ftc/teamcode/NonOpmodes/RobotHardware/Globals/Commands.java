package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.CommandType;
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.OpModeType;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Drive;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.FourBar;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.SideObjective;

@Config
public class Commands {
    public Differential differential;
    public Claw claw;
    public FourBar fourBar;
    public Lift lift;
    public Extension extension;
    public Drive drive;
    public Intake intake;

    private ElapsedTime timer = new ElapsedTime();
    private Gamepad gamepad;

    public static int depositClawDelay = 100;
    public static int depositDifferentialDelay = 250;
    public static int liftThreshold = 50;
    public static int initDelay = 500;
    public static int pickupToDepositDelay = 750;

    private int rumbleDuration = 100;

    private boolean threadRunning = false;

    public void initCommands(){

        differential = Differential.getInstance();
        claw = Claw.getInstance();
        fourBar = FourBar.getInstance();
        lift = Lift.getInstance();
        extension = Extension.getInstance();
        drive = Drive.getInstance();
        intake = Intake.getInstance();
    }

    public void initCommands(Gamepad gamepad){
        this.gamepad = gamepad;

        differential = Differential.getInstance();
        claw = Claw.getInstance();
        fourBar = FourBar.getInstance();
        lift = Lift.getInstance();
        extension = Extension.getInstance();
        drive = Drive.getInstance();
        intake = Intake.getInstance();
    }

    public void loopRobot(Gamepad extensionGamepad, Gamepad driveGamepad, Gamepad intakeGamepad, Gamepad liftGamepad){
        extension.loopExtension(extensionGamepad);
        drive.loopDrive(driveGamepad);
        intake.loopIntake(intakeGamepad);
        lift.loopLift(liftGamepad);
    }

    public void extendLift(Lift.LiftState liftState){
        lift.setLiftState(liftState);
    }
    public void toInit(boolean grab){
        Thread initThread = new Thread(() -> {
            timer.reset();
            if(grab){
                claw.setClawState(Claw.ClawState.CLOSE_ONE_PIXEL);
            }
            else{
                claw.setClawState(Claw.ClawState.OPEN);
            }
            while(timer.milliseconds() < initDelay){

            }
            differential.setState(Differential.State.INIT);
            fourBar.setState(FourBar.State.INIT);
            lift.setLiftState(Lift.LiftState.RETRACTED);
        });
        initThread.start();

    }
    private void toIntermediate(CommandType commandType){
            switch(commandType){
                case ASYNC:
                    Thread intermediateThread = new Thread(() -> {
                        lift.setLiftState(Lift.LiftState.RETRACTED);
                        timer.reset();
                        differential.setState(Differential.State.PICKUP);
                        while(timer.milliseconds() < 500){

                        }
                        fourBar.setState(FourBar.State.INTERMEDIATE_PTD);
                        threadRunning = false;

                    });
                    intermediateThread.start();
                    break;
                case BLOCKING:
                    lift.setLiftState(Lift.LiftState.RETRACTED);
                    timer.reset();
                    differential.setState(Differential.State.PICKUP);
                    while(timer.milliseconds() < 500){

                    }
                    fourBar.setState(FourBar.State.INTERMEDIATE_PTD);
                    threadRunning = false;
                    break;
            }
    }
    private void toPickup(CommandType commandType){
        switch(commandType){
            case BLOCKING:
                lift.setLiftState(Lift.LiftState.RETRACTED);
                while(Math.abs(lift.getCurrentPosition() - lift.getTargetPosition()) >= liftThreshold){
                }
                claw.setClawState(Claw.ClawState.OPEN);
                differential.setState(Differential.State.PICKUP);
                fourBar.setState(FourBar.State.PICKUP);
                threadRunning = false;
                break;
            case ASYNC:
                Thread pickupThread = new Thread(() -> {
                    lift.setLiftState(Lift.LiftState.RETRACTED);
                    while(Math.abs(lift.getCurrentPosition() - lift.getTargetPosition()) >= liftThreshold){
                    }
                    claw.setClawState(Claw.ClawState.OPEN);
                    differential.setState(Differential.State.PICKUP);
                    fourBar.setState(FourBar.State.PICKUP);
                    threadRunning = false;
                });
                pickupThread.start();
                break;
        }
        //Pickup code here

    }

    private void toDeposit(CommandType commandType){
            switch(commandType){
                case ASYNC:
                    Thread depositThread = new Thread(() -> {
                        timer.reset();
                        claw.setClawState(Claw.ClawState.CLOSE);
                        while(timer.milliseconds() < depositClawDelay){

                        }
                        fourBar.setState(FourBar.State.DEPOSIT);
                        while(timer.milliseconds() < depositDifferentialDelay + depositClawDelay){

                        }
                        differential.setState(Differential.State.DEPOSIT);

                        threadRunning = false;
                    });
                    depositThread.start();
                    break;
                case BLOCKING:
                    timer.reset();
                    claw.setClawState(Claw.ClawState.CLOSE);
                    while(timer.milliseconds() < depositClawDelay){

                    }
                    fourBar.setState(FourBar.State.DEPOSIT);
                    while(timer.milliseconds() < depositDifferentialDelay + depositClawDelay){

                    }
                    differential.setState(Differential.State.DEPOSIT);
                    threadRunning = false;
                    break;
            }

        }




    private void runFullSequence(CommandType commandType){
        switch(commandType){
            case ASYNC:
                Thread sequenceThread = new Thread(() -> {
                    claw.setClawState(Claw.ClawState.OPEN);
                    differential.setState(Differential.State.PICKUP);
                    fourBar.setState(FourBar.State.PICKUP);

                    timer.reset();
                    while(timer.milliseconds() < pickupToDepositDelay){

                    }
                    claw.setClawState(Claw.ClawState.CLOSE);
                    timer.reset();
                    while(timer.milliseconds() < depositClawDelay){

                    }
                    fourBar.setState(FourBar.State.DEPOSIT);
                    timer.reset();
                    while(timer.milliseconds() < depositDifferentialDelay){

                    }
                    differential.setState(Differential.State.DEPOSIT_VERTICAL);
                    threadRunning = false;

                });
                sequenceThread.start();
                break;
            case BLOCKING:

                claw.setClawState(Claw.ClawState.OPEN);
                differential.setState(Differential.State.PICKUP);
                fourBar.setState(FourBar.State.PICKUP);

                timer.reset();
                while(timer.milliseconds() < pickupToDepositDelay){

                }
                claw.setClawState(Claw.ClawState.CLOSE);
                timer.reset();
                while(timer.milliseconds() < depositClawDelay){

                }
                fourBar.setState(FourBar.State.DEPOSIT);
                timer.reset();
                while(timer.milliseconds() < depositDifferentialDelay){

                }
                differential.setState(Differential.State.DEPOSIT_VERTICAL);
                threadRunning = false;
                break;
        }
    }

    private void releasePixels(CommandType commandType){
        switch(commandType){
            case ASYNC:
                Thread sequenceThread = new Thread(() -> {
                    timer.reset();
                    claw.setClawState(Claw.ClawState.OPEN);
                    while(timer.milliseconds() < 100){

                    }
                    fourBar.setState(FourBar.State.POST_DEPOSIT);
                    threadRunning = false;
                });

                sequenceThread.start();
                break;
            case BLOCKING:
                timer.reset();
                claw.setClawState(Claw.ClawState.OPEN);
                while(timer.milliseconds() < 100){

                }
                fourBar.setState(FourBar.State.POST_DEPOSIT);
                threadRunning = false;
                break;
        }

    }
    private void releasePixelsToIntermediate(CommandType commandType){
        switch(commandType){
            case ASYNC:
                Thread sequenceThread = new Thread(() -> {
                    timer.reset();
                    claw.setClawState(Claw.ClawState.OPEN);
                    while(timer.milliseconds() < 500){

                    }
                    toIntermediate(CommandType.ASYNC);
                    threadRunning = false;
                });

                sequenceThread.start();
                break;
            case BLOCKING:
                timer.reset();
                claw.setClawState(Claw.ClawState.OPEN);
                while(timer.milliseconds() < 500){

                }
                toIntermediate(CommandType.BLOCKING);
                threadRunning = false;
                break;
        }

    }

    public void releasePixelsToIntermediate(OpModeType opModeType, CommandType commandType){
        if(opModeType == OpModeType.TELEOP){
            if(threadRunning == false){
                threadRunning = true;
                releasePixelsToIntermediate(commandType);

            }
            else{
                gamepad.rumble(rumbleDuration);
            }
        }
        else if(opModeType == OpModeType.AUTONOMOUS){
            releasePixelsToIntermediate(commandType);
        }

    }
    public void releasePixels(OpModeType opModeType, CommandType commandType){
        if(opModeType == OpModeType.TELEOP){
            if(threadRunning == false){
                threadRunning = true;
                releasePixels(commandType);
            }
            else{
                gamepad.rumble(rumbleDuration);
            }
        }
        else if(opModeType == OpModeType.AUTONOMOUS){
            releasePixels(commandType);
        }
    }
    public void runFullSequence(OpModeType opModeType, CommandType commandType){
        if(opModeType == OpModeType.TELEOP){
            if(threadRunning == false){
                threadRunning = true;
                runFullSequence(commandType);
            }
            else{
                gamepad.rumble(rumbleDuration);
            }
        }
        else if(opModeType == OpModeType.AUTONOMOUS){
            runFullSequence(commandType);
        }

    }
    public void toPickup(OpModeType opModeType, CommandType commandType){
        if(opModeType == OpModeType.TELEOP){
            if(threadRunning == false){
                threadRunning = true;
                toPickup(commandType);
            }
            else{
                gamepad.rumble(rumbleDuration);
            }
        }
        else if(opModeType == OpModeType.AUTONOMOUS){
            toPickup(commandType);
        }
    }
    public void toIntermediate(OpModeType opModeType, CommandType commandType){
        if(opModeType == OpModeType.TELEOP){
            if(threadRunning == false){
                threadRunning = true;
                toIntermediate(commandType);
            }
            else{
                gamepad.rumble(rumbleDuration);
            }
        }
        else if(opModeType == OpModeType.AUTONOMOUS){
            toIntermediate(commandType);
        }

    }
    public void toDeposit(OpModeType opModeType, CommandType commandType){
        if(opModeType == OpModeType.TELEOP){
            if(threadRunning == false){
                threadRunning = true;
                toDeposit(commandType);
            }
            else{
                gamepad.rumble(rumbleDuration);
            }
        }
        else if(opModeType == OpModeType.AUTONOMOUS){
            toDeposit(commandType);
        }

    }

    public int getLiftPosition(){
        return lift.getCurrentPosition();
    }
}
