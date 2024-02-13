package org.firstinspires.ftc.teamcode.Opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Commands;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.SideObjective;

//Welcome to Hell
@TeleOp(name="QBTeleOp")
public class QBTeleOp extends OpMode {
    Hardware hw = new Hardware();
    Gamepad currentGamepad2 = new Gamepad(), previousGamepad2 = new Gamepad();
    Commands commands = new Commands();
    SideObjective sideObjective;
    int liftCounter = 0;
    int diffCounter = 0;




    @Override
    public void init() {
        hw.initAll(hardwareMap);
        commands.initCommands();
        commands.toInit(true);
        sideObjective = SideObjective.getInstance();

    }



    @Override
    public void loop() {

        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);

        commands.loopRobot(gamepad2, gamepad2, gamepad1, gamepad1);
        liftCounter = Math.max(0, Math.min(liftCounter, 3));
        diffCounter = Math.max(-1, Math.min(diffCounter ,1));

        if(currentGamepad2.a && !previousGamepad2.a){
            commands.toDeposit();
        }
        if(currentGamepad2.b && !previousGamepad2.b){
            commands.toPickup();
        }
        if(currentGamepad2.y && !previousGamepad2.y){
            commands.releasePixelsToIntermediate();
        }

        if(currentGamepad2.dpad_up && !previousGamepad2.dpad_up){
            liftCounter++;
        }
        if(currentGamepad2.dpad_down && !previousGamepad2.dpad_down){
            liftCounter--;
        }

        /*if(currentGamepad2.dpad_right && !previousGamepad2.dpad_right){
            commands.setDifferential(Differential.State.TILT_RIGHT);
        }
        if(currentGamepad2.dpad_left && !previousGamepad2.dpad_left){
            commands.setDifferential(Differential.State.TILT_LEFT);
        }*/


        if(liftCounter == 0){
            commands.extendLift(Lift.LiftState.RETRACTED);
        }
        else if(liftCounter == 1){
            commands.extendLift(Lift.LiftState.LOW);
        }
        else if(liftCounter == 2){
            commands.extendLift(Lift.LiftState.MED);
        }
        else if(liftCounter == 3){
            commands.extendLift(Lift.LiftState.HIGH);
        }





        if(gamepad1.back){
            sideObjective.setClimbServoPower(1);
        }
        else if(gamepad1.start){
            sideObjective.setClimbServoPower(-1);
        }
        else{
            sideObjective.setClimbServoPower(0);
        }
    }
}
