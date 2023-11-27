package org.firstinspires.ftc.teamcode.MechanismTests;

import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.primArmDropoff;
import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.primArmPickup;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.Hardware;

@TeleOp(name="DropoffTest")
public class DropoffTest extends OpMode {
    Servo primArmL, primArmR, secArmL, secArmR;
    Hardware hardware = new Hardware();
    double secArmPos = 1;
    @Override
    public void init() {
        hardware.initPickup(hardwareMap);
        primArmL = hardware.primArmL;
        primArmR = hardware.primArmR;
        secArmL = hardware.secArmL;
        secArmR = hardware.secArmR;

        primArmL.setPosition(primArmDropoff);
        primArmR.setPosition(primArmDropoff);


    }

    @Override
    public void loop() {
        if(gamepad1.b){
            primArmL.setPosition(primArmDropoff);
            primArmR.setPosition(primArmDropoff);
        }
        if (gamepad1.a) {
            primArmL.setPosition(primArmPickup);
            primArmR.setPosition(primArmPickup);
        }

        if(gamepad1.x){
            secArmPos += 0.01;
        }
        if(gamepad1.y){
            secArmPos -= 0.01;
        }
        secArmL.setPosition(secArmPos);
        secArmR.setPosition(secArmPos);
        telemetry.addData("PrimArmPos: ", primArmR.getPosition());
        telemetry.addData("SecArmPos: ", secArmR.getPosition());
        }
    }

