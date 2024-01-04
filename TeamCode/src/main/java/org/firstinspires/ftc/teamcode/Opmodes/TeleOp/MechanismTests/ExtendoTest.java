package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Extension;

@TeleOp(name="ExtendoTest", group="Tests")
public class ExtendoTest extends OpMode {
    FtcDashboard dashboard;

    Extension extendo;
    enum TuningMode {
        FINE_TUNE,
        OPERATIONAL
    }
    TuningMode tuningMode = TuningMode.FINE_TUNE;
    @Override
    public void init() {
        dashboard = FtcDashboard.getInstance();
        extendo = new Extension(hardwareMap);
    }
    @Override
    public void loop() {
        switch (tuningMode){
            case FINE_TUNE:
                extendo.setPower(gamepad1.left_stick_y*0.5);
                if(gamepad1.left_bumper)
                    tuningMode = TuningMode.OPERATIONAL;
                break;
            case OPERATIONAL:
                //insert target position code
                extendo.loopExtension();
                if(gamepad1.right_bumper)
                    tuningMode = TuningMode.FINE_TUNE;
                break;
        }
        telemetry.addData("Tuning Mode: ", tuningMode);
        telemetry.addData("Current Position: ",extendo.getCurrentPosition());
        telemetry.addData("Target Position: ", Extension.targetPosition);
        telemetry.addData("Error: ", Extension.targetPosition - extendo.getCurrentPosition());

    }
}
