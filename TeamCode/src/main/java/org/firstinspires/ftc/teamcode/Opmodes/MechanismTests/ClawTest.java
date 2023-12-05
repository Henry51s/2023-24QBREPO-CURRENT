package org.firstinspires.ftc.teamcode.Opmodes.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@TeleOp(name="ClawTest")
public class ClawTest extends OpMode {
    Servo claw1, claw2;
    CRServo abc;
    Hardware hardware = new Hardware();
    double pos = 0.5;
    @Override
    public void init() {
        hardware.initDeposit(hardwareMap);
        claw1 = hardware.claw1;
        claw2 = hardware.claw2;

        claw1.setPosition(pos);
        claw2.setPosition(pos);
    }

    @Override
    public void loop() {

        telemetry.addData("Claw pos: ", claw1.getPosition());

        if(gamepad1.b){
            pos += 0.001;
        }
        else if(gamepad1.a){
            pos -= 0.001;
        }
        if(pos >= 1){
            pos = 1;
        }
        else if(pos <= 0){
            pos = 0;
        }
        claw1.setPosition(pos);
        claw2.setPosition(pos);

        }
    }
