package org.firstinspires.ftc.teamcode.Opmodes.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@TeleOp(name="DifferentialTest")
public class DifferentialTest extends OpMode {
    Servo secArmL, secArmR;
    Hardware hardware = new Hardware();
    double pos = 0;

    @Override
    public void init() {
        hardware.initDeposit(hardwareMap);
        secArmL = hardware.diffL;
        secArmR = hardware.diffR;

    }

    @Override
    public void loop() {
        if(gamepad1.a){
            pos += 0.01;
        }
        if(gamepad1.b){
            pos -= 0.01;
        }

        secArmL.setPosition(pos);
        secArmR.setPosition(pos);
        telemetry.addData("secArmL Pos: ", secArmL.getPosition());
        telemetry.addData("secArmR Pos: ", secArmR.getPosition());

    }
}
