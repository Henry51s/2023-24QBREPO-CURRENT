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


    private int pickupLiftRetractDelay = 5000;
    private int depositLiftExtendDelay = 5000;

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

        }
        toDeposit(Lift.LiftState.RETRACTED);
    }

    public void toPickup(){
        //Pickup code here
        Thread pickupThread = new Thread(() -> {
            timer.reset();
            lift.setLiftState(Lift.LiftState.RETRACTED);

            differential.setDiffState(Differential.DiffState.PICKUP);
            claw.setClawState(Claw.ClawState.OPEN);
            fourBar.setFourBarState(FourBar.FourBarState.PICKUP);
        });
        pickupThread.start();
    }

    public void toDeposit(Lift.LiftState liftState){
        //Deposit code here
        Thread depositThread = new Thread(() -> {
            differential.setDiffState(Differential.DiffState.DEPOSIT);
            fourBar.setFourBarState(FourBar.FourBarState.DEPOSIT);

            lift.setLiftState(liftState);
        });
        depositThread.start();

    }

    public void releasePixels(){
        claw.setClawState(Claw.ClawState.OPEN);
    }
}
