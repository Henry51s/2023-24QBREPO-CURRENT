package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.CommandType;
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
    private Differential differential;
    private Claw claw;
    private FourBar fourBar;
    private Lift lift;
    private Extension extension;
    private Drive drive;
    private Intake intake;

    private ElapsedTime timer = new ElapsedTime();

    public static int depositClawDelay = 100;
    public static int depositDifferentialDelay = 250;
    public static int liftThreshold = 50;
    public static int initDelay = 500;
    public static int pickupToDepositDelay = 750;

    public void initCommands(){

            differential = Differential.getInstance();
            claw = Claw.getInstance();
            fourBar = FourBar.getInstance();
            lift = Lift.getInstance();
            extension = Extension.getInstance();
            drive = Drive.getInstance();
            intake = Intake.getInstance();

    }

    public void loopRobot(Gamepad differentialGamepad, Gamepad extensionGamepad, Gamepad driveGamepad, Gamepad intakeGamepad, Gamepad liftGamepad){
        //differential.loopDifferential(differentialGamepad);
        extension.loopExtension(extensionGamepad);
        drive.loopDrive(driveGamepad);
        intake.loopIntake(intakeGamepad);
        lift.loopLift(liftGamepad);
    }

    public synchronized void extendLift(Lift.LiftState liftState){
        lift.setLiftState(liftState);
    }
    public synchronized void toInit(boolean grab){
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
    public synchronized void toIntermediate(){
        Thread intermediateThread = new Thread(() -> {
            lift.setLiftState(Lift.LiftState.RETRACTED);
            fourBar.setState(FourBar.State.INTERMEDIATE_PTD);
            differential.setState(Differential.State.PICKUP);
        });
        intermediateThread.start();
    }
    public synchronized void toPickup(){
        //Pickup code here
        Thread pickupThread = new Thread(() -> {
            lift.setLiftState(Lift.LiftState.RETRACTED);
            while(Math.abs(lift.getCurrentPosition() - lift.getTargetPosition()) >= liftThreshold){
            }
            claw.setClawState(Claw.ClawState.OPEN);
            differential.setState(Differential.State.PICKUP);
            fourBar.setState(FourBar.State.PICKUP);

        });
        pickupThread.start();
    }

    public synchronized void toDeposit(){
        //Deposit code here
        Thread depositThread = new Thread(() -> {
            timer.reset();
            claw.setClawState(Claw.ClawState.CLOSE);
            while(timer.milliseconds() < depositClawDelay){

            }
            fourBar.setState(FourBar.State.DEPOSIT);
            while(timer.milliseconds() < depositDifferentialDelay + depositClawDelay){

            }
            differential.setState(Differential.State.DEPOSIT);
            lift.setLiftState(Lift.LiftState.MEMORY);

        });
        depositThread.start();
    }

    public synchronized void runFullSequence(CommandType commandType){
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
                break;
        }
    }

    public synchronized void releasePixels(){

        Thread sequenceThread = new Thread(() -> {
            timer.reset();
            claw.setClawState(Claw.ClawState.OPEN);
            while(timer.milliseconds() < 100){

            }
            fourBar.setState(FourBar.State.POST_DEPOSIT);
        });

        sequenceThread.start();
    }
    public synchronized void releasePixelsToIntermediate(){
        Thread sequenceThread = new Thread(() -> {
            timer.reset();
            claw.setClawState(Claw.ClawState.OPEN);
            while(timer.milliseconds() < 500){

            }
            toIntermediate();
        });

        sequenceThread.start();
    }
    public int getLiftPosition(){
        return lift.getCurrentPosition();
    }
    public Lift.LiftState getLiftState(){
        return lift.getLiftState();
    }
    public FourBar.State getFourBarState(){
        return fourBar.getState();
    }
    public Differential.State getDifferentialState(){
        return differential.getState();
    }
    public Claw.ClawState getClawState(){
        return claw.getClawState();
    }
}
