package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Drive;

@TeleOp(name="DriveTest", group="Tests")
public class DriveTest extends OpMode {
    Drive drive;
    @Override
    public void init() {
        drive = Drive.getInstance();
        drive.initDrive(hardwareMap);
    }

    @Override
    public void loop() {
        drive.loopDrive(gamepad1);

        telemetry.addData("Front Left Power: ", drive.getMotorPowers()[0]);
        telemetry.addData("Front Right Power: ", drive.getMotorPowers()[1]);
        telemetry.addData("Back Left Power: ", drive.getMotorPowers()[2]);
        telemetry.addData("Back Right Power: ", drive.getMotorPowers()[3]);
    }
}
