package org.firstinspires.ftc.teamcode.Opmodes.TeleOp;

import static org.firstinspires.ftc.teamcode.NonOpmodes.Enums.OpModeType.TELEOP;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.CommandType;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Commands;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.SideObjective;

//Welcome to Hell
@TeleOp(name="QBTeleOp")
public class QBTeleOp extends OpMode {
    Hardware hw = new Hardware();
    Gamepad currentGamepad2 = new Gamepad(), previousGamepad2 = new Gamepad();
    Differential differential;

    Commands commands = new Commands();
    SideObjective sideObjective;
    int diffCounter = 0;




    @Override
    public void init() {
        hw.initAll(hardwareMap);
        commands.initCommands(gamepad2);
        differential = Differential.getInstance();
        commands.toInit(true);
        sideObjective = SideObjective.getInstance();

    }



    @Override
    public void loop() {

        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);

        diffCounter = Math.max(0, Math.min(diffCounter, 3));

        commands.loopRobot(gamepad2, gamepad1, gamepad1, gamepad2);

        if(currentGamepad2.dpad_left && !previousGamepad2.dpad_left){
            diffCounter--;
        }
        if(currentGamepad2.dpad_right && !previousGamepad2.dpad_right){
            diffCounter++;
        }

        if(differential.getState() == Differential.State.DEPOSIT || differential.getState() == Differential.State.TILT_LEFT || differential.getState() == Differential.State.TILT_RIGHT || differential.getState() == Differential.State.DEPOSIT_VERTICAL){
            if(diffCounter == 0){
                differential.setState(Differential.State.TILT_LEFT);
            }
            else if(diffCounter == 1){
                differential.setState(Differential.State.DEPOSIT);
            }
            else if(diffCounter == 2){
                differential.setState(Differential.State.TILT_RIGHT);
            }
            else if(diffCounter == 3){
                differential.setState(Differential.State.DEPOSIT_VERTICAL);
            }
        }

        if(currentGamepad2.a && !previousGamepad2.a){
            commands.toDeposit(TELEOP, CommandType.ASYNC);
        }
        if(currentGamepad2.b && !previousGamepad2.b){
            commands.toPickup(TELEOP, CommandType.ASYNC);
        }
        if(currentGamepad2.y && !previousGamepad2.y){
            commands.releasePixelsToIntermediate(TELEOP, CommandType.ASYNC);
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
        if(gamepad1.dpad_left && gamepad1.b){
            sideObjective.releaseDrone();
        }
        telemetry.addData("Differential state: ", commands.differential.getState());

        telemetry.addData("Front Left Power: ", commands.drive.getMotorPowers()[0]);
        telemetry.addData("Front Right Power: ", commands.drive.getMotorPowers()[1]);
        telemetry.addData("Back Left Power: ", commands.drive.getMotorPowers()[2]);
        telemetry.addData("Back Right Power: ", commands.drive.getMotorPowers()[3]);
    }
}
