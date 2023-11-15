package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.Hardware;

public class ClawTest extends OpMode {
    Servo claw1, claw2;
    Hardware hardware = new Hardware();
    @Override
    public void init() {
        hardware.initPickup(hardwareMap);
        claw1 = hardware.claw1;
        claw2 = hardware.claw2;

    }

    @Override
    public void loop() {

    }
}
