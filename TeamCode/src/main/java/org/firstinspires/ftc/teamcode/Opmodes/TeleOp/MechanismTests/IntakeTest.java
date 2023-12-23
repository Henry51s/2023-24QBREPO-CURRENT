package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.MOTOR_5;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.MOTOR_6;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Intake;

@TeleOp(name="IntakeTest", group="Tests")
public class IntakeTest extends OpMode {

    Intake intake;
    @Override
    public void init() {
        intake = new Intake(hardwareMap);
    }

    @Override
    public void loop() {
        intake.loopIntake(gamepad1);
    }
}
