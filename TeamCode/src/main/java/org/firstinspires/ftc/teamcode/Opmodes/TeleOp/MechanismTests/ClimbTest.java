package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;

@TeleOp(name="ClimbTest")
public class ClimbTest extends OpMode {
    Servo climb1, climb2;
    Hardware hw = new Hardware();
    double position = 0.5;
    @Override
    public void init() {
        hw.initClimb(hardwareMap);
        climb1 = hw.climb1;
        climb2 = hw.climb2;
    }

    @Override
    public void loop() {
        climb1.setPosition(position);
        climb2.setPosition(position);
        if(gamepad1.dpad_up){
            position += 0.001;
        }
        if(gamepad1.dpad_down){
            position -= 0.001;
        }
        telemetry.addData("Position: ", position);

    }
}
