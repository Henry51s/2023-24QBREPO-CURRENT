package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.driveMultiplier;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.Hardware;

@TeleOp(name="QBTeleOp")
public class QBTeleOp extends OpMode {

    DcMotor frontLeft, frontRight, backLeft,backRight;
    Hardware hardware = new Hardware();

    @Override
    public void init() {

        hardware.initDrive(hardwareMap);
        frontLeft = hardware.frontLeft;
        frontRight = hardware.frontRight;
        backLeft = hardware.backLeft;
        backRight = hardware.backRight;

    }

    @Override
    public void loop() {


        double y = -gamepad1.left_stick_y*driveMultiplier; // Remember, Y stick is reversed!
        double x = gamepad1.left_stick_x*driveMultiplier;
        double rx = -gamepad1.right_stick_x*driveMultiplier;

        double ry = -gamepad1.right_stick_y;

        frontLeft.setPower(y + x + rx);
        backLeft.setPower(y - x + rx);
        frontRight.setPower(y - x - rx);
        backRight.setPower(y + x - rx);

    }
}
