package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.TuningModes;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension;

@Config
@TeleOp(name="ExtendoTest", group="Tests")
public class ExtendoTest extends OpMode {
    FtcDashboard dashboard;

    Extension extendo;

    TuningModes tuningMode = TuningModes.OPERATIONAL;
    @Override
    public void init() {
        dashboard = FtcDashboard.getInstance();
        extendo = Extension.getInstance();
        extendo.initExtension(hardwareMap);
    }
    @Override
    public void loop() {
        switch (tuningMode){
            case FINE_TUNE:
                if(gamepad1.left_stick_button){
                    tuningMode = TuningModes.OPERATIONAL;
                }
                break;

            case OPERATIONAL:
                //insert target position code
                //extendo.setTargetPosition(targetPosition);
                extendo.loopExtension(gamepad1);
                if(gamepad1.right_stick_button){
                    tuningMode = TuningModes.FINE_TUNE;
                }
                break;
        }
        telemetry.addData("Tuning Mode: ", tuningMode);
        telemetry.addData("CurrentL Position: ",extendo.getMotorLCurrentPosition());
        telemetry.addData("CurrentR Position: ", extendo.getMotorRCurrentPosition());
        telemetry.addData("Target Position: ", extendo.targetPosition);
        //telemetry.addData("Error: ", Extension.targetPosition - extendo.getCurrentPosition());

    }
}
