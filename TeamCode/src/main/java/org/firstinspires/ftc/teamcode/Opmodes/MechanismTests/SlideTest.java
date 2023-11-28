package org.firstinspires.ftc.teamcode.Opmodes.MechanismTests;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.scoreMax;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@TeleOp(name="SlideTest")
public class SlideTest extends OpMode {
    DcMotor slide;
    Hardware hardware = new Hardware();
    @Override
    public void init() {
        hardware.initDeposit(hardwareMap);
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
