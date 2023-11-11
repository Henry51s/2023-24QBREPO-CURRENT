package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.Hardware;

@TeleOp(name="IntakeTest")
public class IntakeTest extends OpMode {

    CRServo intakeLeft, intakeRight;
    Servo linearLeft, linearRight;
    Hardware hardware = new Hardware();
    double pos = 1;

    @Override
    public void init() {
        hardware.initIntake(hardwareMap);
        intakeLeft = hardware.intakeLeft;
        intakeRight = hardware.intakeRight;
        linearLeft = hardware.linearLeft;
        linearRight = hardware.linearRight;
    }

    @Override
    public void loop() {
        double joyLeft = gamepad1.left_stick_y;
        double joyRight = gamepad1.right_stick_y;
        pos += joyRight*0.01;
        intakeLeft.setPower(joyLeft);
        intakeRight.setPower(joyLeft);
        linearLeft.setPosition(pos);
        linearRight.setPosition(pos);

    }
}
