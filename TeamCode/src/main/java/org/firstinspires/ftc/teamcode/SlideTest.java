package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.NonOpmodes.UtilConstants.scoreMax;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.NonOpmodes.Hardware;

@TeleOp(name="SlideTest")
public class SlideTest extends OpMode {
    DcMotor slide;
    Hardware hardware = new Hardware();
    @Override
    public void init() {
        hardware.initPickup(hardwareMap);
        slide = hardware.slide;
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            slide.setTargetPosition(scoreMax);
        }
        telemetry.addData("Pos: ", slide.getCurrentPosition());

    }
}
