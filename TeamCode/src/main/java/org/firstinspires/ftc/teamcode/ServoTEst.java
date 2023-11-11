package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.Hardware;

@TeleOp(name="ServoTest")
public class ServoTEst extends OpMode {
    Hardware hardware = new Hardware();
    Servo arm, claw;
    double pos1 = 0;
    double pos2 = 0;
    @Override
    public void init() {
        hardware.initPickup(hardwareMap);
        arm = hardware.arm;
        claw = hardware.claw;
    }

    @Override
    public void loop() {
        double leftY = gamepad1.left_stick_y;
        double rightY = gamepad1.right_stick_y;
        pos1 += 0.001*leftY;
        pos2 += 0.001*rightY;
        arm.setPosition(pos1);
        claw.setPosition(pos2);

        telemetry.addData("Arm Pos: ", arm.getPosition());
        telemetry.addData("Claw Pos: ", claw.getPosition());


    }
}
