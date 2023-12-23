package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@TeleOp(name="LinearServoTest",group="Tests")
public class LinearServoTest extends OpMode {
    Servo linearLeft, linearRight;
    Hardware hardware = new Hardware();
    double pos = 1;
    @Override
    public void init() {
        hardware.initIntake(hardwareMap);
        linearLeft = hardware.linearLeft;
        linearRight = hardware.linearRight;

    }

    @Override
    public void loop() {
        if(gamepad1.a){
            pos += 0.01;
        }
        if(gamepad1.b){
            pos -= 0.01;
        }
        linearLeft.setPosition(pos);
        linearRight.setPosition(pos);
        telemetry.addData("LinearLeft Pos: ", linearLeft.getPosition());
        telemetry.addData("LinearRight Pos: ", linearRight.getPosition());


    }
}
