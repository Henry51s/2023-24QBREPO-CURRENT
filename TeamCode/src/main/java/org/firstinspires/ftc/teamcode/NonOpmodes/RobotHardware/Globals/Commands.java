package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.FourBarDifferentialStates;
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
    Differential differential;
    Claw claw;
    FourBar fourBar;
    Lift lift;
    Extension extension;
    Drive drive;
    Intake intake;
    SideObjective sideObjective;

    ElapsedTime timer = new ElapsedTime();
    Telemetry telemetry;

    public static int depositClawDelay = 100;
    public static int depositDifferentialDelay = 250;
    public static int liftThreshold = 50;
    public static int initDelay = 500;
    public static int pickupToDepositDelay = 750;

    public void initCommands(Telemetry telemetry){
        this.telemetry = telemetry;
        try{
            differential = Differential.getInstance();
            claw = Claw.getInstance();
            fourBar = FourBar.getInstance();
            lift = Lift.getInstance();
            extension = Extension.getInstance();
            drive = Drive.getInstance();
            intake = Intake.getInstance();
            sideObjective = SideObjective.getInstance();
        }
        catch(Exception e){
            //Put stuff incase error happens here
            telemetry.addData("Command initalization error!!! ", e);
            telemetry.update();
        }
    }

    public void loopRobot(Gamepad differentialGamepad, Gamepad extensionGamepad, Gamepad driveGamepad, Gamepad intakeGamepad){
        differential.loopDifferential(differentialGamepad);
        extension.loopExtension(extensionGamepad);
        drive.loopDrive(driveGamepad);
        intake.loopIntake(intakeGamepad);
    }
    public synchronized void latchClimbAndDrone(){
        sideObjective.latchClimb();
        sideObjective.latchDrone();

    }
    public synchronized void releaseClimbAndDrone(int delay){
        Thread sideThread = new Thread(() -> {
            timer.reset();
            sideObjective.releaseClimb();
            while(timer.milliseconds() < delay){

            }
            sideObjective.releaseDrone();
        });
        sideThread.start();
    }
    public synchronized void extendLift(Lift.LiftState liftState){
        lift.setLiftState(liftState);
    }
    public synchronized void toInit(boolean grab){
        Thread initThread = new Thread(() -> {
            timer.reset();
            latchClimbAndDrone();
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

        });
        depositThread.start();
    }

    public synchronized void runFullSequence(){
        Thread sequenceThread = new Thread(() -> {
            lift.setLiftState(Lift.LiftState.RETRACTED);
            while(Math.abs(lift.getCurrentPosition() - lift.getTargetPosition()) >= liftThreshold){
            }
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
            differential.setState(Differential.State.DEPOSIT);


        });
        sequenceThread.start();
    }

    public synchronized void releasePixels(){
        claw.setClawState(Claw.ClawState.OPEN);
    }
    public synchronized void releasePixelsToIntermediate(){
        Thread sequenceThread = new Thread(() -> {
            timer.reset();
            claw.setClawState(Claw.ClawState.OPEN);
            while(timer.milliseconds() < 100){

            }
            toIntermediate();
        });

        sequenceThread.start();
    }
    public int getLiftPosition(){
        return lift.getCurrentPosition();
    }
}
