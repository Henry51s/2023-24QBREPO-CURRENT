package org.firstinspires.ftc.teamcode.MechanismTests;

import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.SERVO_2;
import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.SERVO_8;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.Hardware;

@TeleOp(name="DifferentialTest")
public class DifferentialTest extends OpMode {
    Servo secArmL, secArmR;
    Hardware hardware = new Hardware();
    double pos = 0;

    @Override
    public void init() {
        hardware.initPickup(hardwareMap);
        secArmL = hardware.secArmL;
        secArmR = hardware.secArmR;

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
