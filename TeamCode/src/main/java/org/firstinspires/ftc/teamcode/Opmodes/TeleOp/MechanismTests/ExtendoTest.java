package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Extension;

@Config
@TeleOp(name="ExtendoTest", group="Tests")
public class ExtendoTest extends OpMode {
    FtcDashboard dashboard;

    Extension extendo;
    public static int targetPosition = 0;
    enum TuningMode {
        FINE_TUNE,
        OPERATIONAL
    }
    TuningMode tuningMode = TuningMode.OPERATIONAL;
    @Override
    public void init() {
        dashboard = FtcDashboard.getInstance();
        extendo = new Extension(hardwareMap);
    }
    @Override
    public void loop() {
        switch (tuningMode){

            case OPERATIONAL:
                //insert target position code
                //extendo.setTargetPosition(targetPosition);


                break;
        }
        telemetry.addData("Tuning Mode: ", tuningMode);
        telemetry.addData("Current Position: ",extendo.getCurrentPosition());
        telemetry.addData("Target Position: ", Extension.targetPosition);
        telemetry.addData("Error: ", Extension.targetPosition - extendo.getCurrentPosition());

    }
}
