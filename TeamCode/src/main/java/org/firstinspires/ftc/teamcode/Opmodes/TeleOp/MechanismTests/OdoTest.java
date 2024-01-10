package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;

@TeleOp(name="OdoTest",group="Tests")
public class OdoTest extends OpMode {
    DcMotor parallel, perp;
    Hardware hardware = new Hardware();
    @Override
    public void init() {
        hardware.initDrive(hardwareMap);
        parallel = hardware.backLeft;
        perp = hardware.frontLeft;
    }

    @Override
    public void loop() {
        telemetry.addData("Parallel: ", parallel.getCurrentPosition());
        telemetry.addData("Perp: ", perp.getCurrentPosition());
    }
}
