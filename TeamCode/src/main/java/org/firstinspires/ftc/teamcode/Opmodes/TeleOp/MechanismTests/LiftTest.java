package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;


import static org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests.LiftTestConstants.POWER;
import static org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests.LiftTestConstants.TARGET;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.PIDMotor;

@Config
@TeleOp(name="LiftTest",group="Tests")
public class LiftTest extends OpMode {

    //max is 1781
    DcMotor lift;
    Hardware hardware = new Hardware();
    FtcDashboard dashboard;
    TelemetryPacket packet;

    @Override
    public void init() {
        hardware.initDeposit(hardwareMap);
        lift = hardware.lift;

        dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(25);
        packet = new TelemetryPacket();

    }

    @Override
    public void loop() {
        lift.setPower(POWER);
        lift.setTargetPosition(TARGET);
        telemetry.addData("Lift Position", lift.getCurrentPosition());

        packet.put("Lift Position: ", lift.getCurrentPosition());
        packet.put("Target Position: ", TARGET);
        dashboard.sendTelemetryPacket(packet);
    }
}
