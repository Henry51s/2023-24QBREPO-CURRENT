package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.FourBar;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Lift;

public class Commands {
    Differential differential;
    Claw claw;
    FourBar fourBar;
    Lift lift;

    Hardware hardware = new Hardware();

    ElapsedTime timer = new ElapsedTime();
    Telemetry telemetry;


    private int pickupLiftRetractDelay = 1000;
    private int depositClawDelay = 500;
    private int depositDifferentialDelay = 1000;

    public void initCommands(Telemetry telemetry){
        this.telemetry = telemetry;
        try{
            differential = Differential.getInstance();
            claw = Claw.getInstance();
            fourBar = FourBar.getInstance();
            lift = Lift.getInstance();
        }
        catch(Exception e){
            //Put stuff incase error happens here
            telemetry.addData("Command initalization error!!! ", e);
            telemetry.update();

        }
    }
    public synchronized void toPickup(){
        //Pickup code here
        Thread pickupThread = new Thread(() -> {
            timer.reset();
            lift.setLiftState(Lift.LiftState.RETRACTED);
            while(timer.milliseconds() < pickupLiftRetractDelay){

            }
            differential.setDiffState(Differential.DiffState.PICKUP);
            fourBar.setFourBarState(FourBar.FourBarState.PICKUP);
            claw.setClawState(Claw.ClawState.OPEN);

        });
        pickupThread.start();
    }

    public synchronized void toDeposit(Lift.LiftState liftState){
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
            lift.setLiftState(liftState);
        });
        depositThread.start();
    }

    public synchronized void releasePixels(){
        claw.setClawState(Claw.ClawState.OPEN);
    }
}
