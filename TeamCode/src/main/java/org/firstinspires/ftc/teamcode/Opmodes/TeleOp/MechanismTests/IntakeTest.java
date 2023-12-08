package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.MOTOR_5;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.MOTOR_6;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="IntakeTest")
public class IntakeTest extends OpMode {

    DcMotor intake;
    @Override
    public void init() {
        intake = hardwareMap.get(DcMotor.class, MOTOR_5);
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            intake.setPower(1);
        }
        else if (gamepad1.b){
            intake.setPower(0.65);
        }
        else if(gamepad1.x){
            intake.setPower(0.4);
        }
        else if(gamepad1.y){
            intake.setPower(0.15);
        }
        else if(!gamepad1.a || !gamepad1.b || !gamepad1.x || !gamepad1.y){
            intake.setPower(0);
        }

    }
}
