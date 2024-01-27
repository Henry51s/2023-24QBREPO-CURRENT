package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Commands;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;

@TeleOp(name="CommandTest")
public class CommandTest extends OpMode {
    Hardware hardware = new Hardware();
    Commands commands = new Commands();
    @Override
    public void init() {
        hardware.initAll(hardwareMap);
        commands.initCommands(telemetry);

    }

    @Override
    public void loop() {
        if(gamepad1.a){
            commands.toPickup();
        }

    }
}
