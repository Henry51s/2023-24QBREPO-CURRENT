package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Extension;

@TeleOp(name="ExtendoTest")
public class ExtendoTest extends OpMode {
    FtcDashboard dashboard;

    Extension extendo;
    enum TuningMode {
        MANUAL,
        PID
    }
    TuningMode tuningMode = TuningMode.MANUAL;
    @Override
    public void init() {
        dashboard = FtcDashboard.getInstance();
        extendo = new Extension(hardwareMap);
    }
    @Override
    public void loop() {
        switch (tuningMode){
            case MANUAL:
                extendo.setPower(gamepad1.left_stick_y);
                if(gamepad1.left_bumper)
                    tuningMode = TuningMode.PID;
                break;
            case PID:
                //insert target position code
                extendo.loopExtension();
                if(gamepad1.right_bumper)
                    tuningMode = TuningMode.MANUAL;
                break;
        }
        telemetry.addData("Tuning Mode: ", tuningMode);
        telemetry.addData("Current Position: ",extendo.getCurrentPosition());
        telemetry.addData("Target Position: ", Extension.targetPosition);
        telemetry.addData("Error: ", Extension.targetPosition - extendo.getCurrentPosition());

    }
}
