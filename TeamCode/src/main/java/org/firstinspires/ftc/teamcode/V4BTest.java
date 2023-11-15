package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.Hardware;

@TeleOp(name="v4btest")
public class V4BTest extends OpMode {
    Servo primArmL, primArmR;
    Hardware hardware = new Hardware();
    double pos = 0;
    @Override
    public void init() {
        hardware.initPickup(hardwareMap);
        primArmL = hardware.primArmL;
        primArmR = hardware.primArmR;

    }

    @Override
    public void loop() {
        pos += gamepad1.left_stick_y*0.01;
        primArmL.setPosition(pos);
        primArmR.setPosition(pos);
        telemetry.addData("PosR: ", primArmR.getPosition());
        telemetry.addData("PosL: ", primArmL.getPosition());

    }
}
