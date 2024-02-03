package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension;

@Config
@TeleOp(name="ExtendoTest", group="Tests")
public class ExtendoTest extends OpMode {
    FtcDashboard dashboard;

    Extension extendo;
    enum TuningMode {
        FINE_TUNE,
        OPERATIONAL
    }
    TuningMode tuningMode = TuningMode.OPERATIONAL;
    @Override
    public void init() {
        dashboard = FtcDashboard.getInstance();
        extendo = Extension.getInstance();
        extendo.initExtension(hardwareMap, true);
    }
    @Override
    public void loop() {
        switch (tuningMode){
            case FINE_TUNE:
                if(gamepad1.left_stick_button){
                    tuningMode = TuningMode.OPERATIONAL;
                }
                break;

            case OPERATIONAL:
                //insert target position code
                //extendo.setTargetPosition(targetPosition);
                extendo.loopExtension(gamepad1);
                if(gamepad1.right_stick_button){
                    tuningMode = TuningMode.FINE_TUNE;
                }
                break;
        }
        telemetry.addData("Tuning Mode: ", tuningMode);
        telemetry.addData("Current Position: ",extendo.getCurrentPosition());
        telemetry.addData("Target Position: ", Extension.targetPosition);
        telemetry.addData("Error: ", Extension.targetPosition - extendo.getCurrentPosition());

    }
}
