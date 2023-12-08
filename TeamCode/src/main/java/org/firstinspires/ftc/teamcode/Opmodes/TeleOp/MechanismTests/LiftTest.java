package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;


import static org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests.LiftTestConstants.D;
import static org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests.LiftTestConstants.F;
import static org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests.LiftTestConstants.I;
import static org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests.LiftTestConstants.P;
import static org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests.LiftTestConstants.TARGET;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.PIDMotor;

@Config
@TeleOp(name="LiftTest")
public class LiftTest extends OpMode {

    //max is 1781
    PIDMotor lift;
    Hardware hardware = new Hardware();
    FtcDashboard dashboard;
    TelemetryPacket packet;

    @Override
    public void init() {
        hardware.initDeposit(hardwareMap);
        lift = hardware.lift;
        lift.setDirection(DcMotorSimple.Direction.REVERSE);

        dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(25);
        packet = new TelemetryPacket();

    }

    @Override
    public void loop() {



        lift.setCoefficients(P,I,D,F);
        lift.setTargetPosition(TARGET);
        lift.loopPID();
        telemetry.addData("Lift Position", lift.getCurrentPosition());

        packet.put("Lift Position: ", lift.getCurrentPosition());
        packet.put("Target Position: ", TARGET);
        dashboard.sendTelemetryPacket(packet);
    }
}
