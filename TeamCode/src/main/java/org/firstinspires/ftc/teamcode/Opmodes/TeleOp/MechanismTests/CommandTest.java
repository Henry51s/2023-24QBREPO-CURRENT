package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Commands;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Lift;

@TeleOp(name="CommandTest")
public class CommandTest extends OpMode {
    Hardware hardware = new Hardware();
    Commands commands = new Commands();

    Gamepad current = new Gamepad(), previous = new Gamepad();
    @Override
    public void init() {
        hardware.initAll(hardwareMap);
        commands.initCommands(telemetry);

    }

    @Override
    public void loop() {
        previous.copy(current);
        current.copy(gamepad1);
        if(current.a && !previous.a){
            commands.toPickup();
        }
        if(current.b && !previous.b){
            commands.toDeposit();
        }
        if(current.x && !previous.x){
            commands.releasePixels();

        }    }
}
