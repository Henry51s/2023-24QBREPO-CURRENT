package org.firstinspires.ftc.teamcode.Opmodes.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@TeleOp(name="ClawTest")
public class ClawTest extends OpMode {
    Servo claw1, claw2;
    Hardware hardware = new Hardware();
    double pos1 = 0;
    double pos2 = 0;
    @Override
    public void init() {
        hardware.initDeposit(hardwareMap);
        claw1 = hardware.claw1;
        claw2 = hardware.claw2;

        claw1.setPosition(0);
        claw2.setPosition(0);
    }

    @Override
    public void loop() {
        telemetry.addData("Claw 1 pos: ", claw1.getPosition());
        telemetry.addData("Claw 2 pos: ", claw2.getPosition());

        if(gamepad1.b){
            pos1 += 0.001;
        }
        else if(gamepad1.a){
            pos1 -= 0.001;
        }
        if(pos1 >= 1){
            pos1 = 1;
        }
        else if(pos1 <= 0){
            pos1 = 0;
        }
        claw1.setPosition(pos1);

        if(gamepad1.y){
            pos2 += 0.001;
        }
        else if(gamepad1.x){
            pos2 -= 0.001;
        }
        if (pos2 >= 1) {
            pos2 = 1;
        }
        else if (pos2 <= 0){
            pos2 = 0;
        }
        claw2.setPosition(pos2);
        }
    }

