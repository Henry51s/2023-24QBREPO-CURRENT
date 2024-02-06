package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.FourBar;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.SideObjective;

public class Commands {
    Differential differential;
    Claw claw;
    FourBar fourBar;
    Lift lift;
    Extension extension;
    SideObjective sideObjective;

    ElapsedTime timer = new ElapsedTime();
    Telemetry telemetry;


    private int pickupLiftRetractDelay = 1000;
    private int depositClawDelay = 500;
    private int depositDifferentialDelay = 1000;
    private int liftThreshold = 50;

    public void initCommands(Telemetry telemetry){
        this.telemetry = telemetry;
        try{
            differential = Differential.getInstance();
            claw = Claw.getInstance();
            fourBar = FourBar.getInstance();
            lift = Lift.getInstance();
            extension = Extension.getInstance();
            sideObjective = SideObjective.getInstance();
        }
        catch(Exception e){
            //Put stuff incase error happens here
            telemetry.addData("Command initalization error!!! ", e);
            telemetry.update();
        }
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
    public synchronized void toInit(){
        Thread initThread = new Thread(() -> {
            timer.reset();
            latchClimbAndDrone();
            claw.setClawState(Claw.ClawState.CLOSE_ONE_PIXEL);
            while(timer.milliseconds() < 3000){

            }
            differential.setDiffState(Differential.DiffState.INIT);
            fourBar.setFourBarState(FourBar.FourBarState.INIT);
            lift.setLiftState(Lift.LiftState.RETRACTED);
        });
        initThread.start();

    }
    public synchronized void toPickup(){
        //Pickup code here
        Thread pickupThread = new Thread(() -> {
            timer.reset();
            lift.setLiftState(Lift.LiftState.RETRACTED);
            while(Math.abs(lift.getCurrentPosition() - lift.getTargetPosition()) >= liftThreshold){
            }
            claw.setClawState(Claw.ClawState.OPEN);
            differential.setDiffState(Differential.DiffState.PICKUP);
            fourBar.setFourBarState(FourBar.FourBarState.PICKUP);

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
            fourBar.setFourBarState(FourBar.FourBarState.DEPOSIT);
            while(timer.milliseconds() < depositDifferentialDelay + depositClawDelay){

            }
            differential.setDiffState(Differential.DiffState.DEPOSIT);

        });
        depositThread.start();
    }

    public synchronized void releasePixels(){
        claw.setClawState(Claw.ClawState.OPEN);
    }


    public double getExtensionError(){
        return Math.abs(extension.getMotorLCurrentPosition() - extension.targetPosition);
    }
}
