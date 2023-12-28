package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@TeleOp(name="DriveTest")
public class DriveTest extends OpMode {
    Hardware hardware = new Hardware();
    @Override
    public void init() {
        hardware.initDrive(hardwareMap);
    }

    @Override
    public void loop() {
        hardware.loopDrive(gamepad1);
    }
}
