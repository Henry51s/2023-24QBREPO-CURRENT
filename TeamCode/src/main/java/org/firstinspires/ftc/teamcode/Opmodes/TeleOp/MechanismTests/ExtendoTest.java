package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@TeleOp(name="ExtendoTest")
public class ExtendoTest extends OpMode {
    DcMotor extendoL, extendoR;
    Hardware hardware = new Hardware();
    @Override
    public void init() {
        hardware.initIntake(hardwareMap);
        extendoL = hardware.extendoL;
        extendoR = hardware.extendoR;
    }

    @Override
    public void loop() {
        extendoL.setPower(gamepad1.left_stick_y);
        extendoR.setPower(gamepad1.left_stick_y);
    }
}
