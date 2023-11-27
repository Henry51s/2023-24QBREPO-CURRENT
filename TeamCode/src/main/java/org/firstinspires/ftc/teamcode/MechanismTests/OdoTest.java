package org.firstinspires.ftc.teamcode.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.NonOpmodes.Hardware;

@TeleOp(name="OdoTest")
public class OdoTest extends OpMode {
    DcMotor parallel, perp;
    Hardware hardware = new Hardware();
    @Override
    public void init() {
        hardware.initDrive(hardwareMap);
        parallel = hardware.frontLeft;
        perp = hardware.backRight;
    }

    @Override
    public void loop() {
        telemetry.addData("Parallel: ", parallel.getCurrentPosition());
        telemetry.addData("Perp: ", perp.getCurrentPosition());
    }
}
