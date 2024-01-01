package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.FourBar;

@TeleOp(name="FourBarTest",group="Tests")
public class FourBarTest extends OpMode {
    enum TuneStates{
        FINE_TUNE,
        OPERATIONAL
    }
    TuneStates tuneStates = TuneStates.FINE_TUNE;

    FourBar fourBar;

    double position = 0.5;
    @Override
    public void init() {
        fourBar = new FourBar(hardwareMap);
    }

    @Override
    public void loop() {

        switch(tuneStates){
            case FINE_TUNE:
                fourBar.setV4BPosition(position);
                if(Math.abs(gamepad1.left_stick_y)>0){
                    position += 0.001*Math.signum(gamepad1.left_stick_y);
                }
                if(Math.abs(position) >= 1)
                    position = Math.signum(position);

                if(gamepad1.right_stick_button)
                    tuneStates = TuneStates.OPERATIONAL;
                break;
            case OPERATIONAL:

                if(gamepad2.left_stick_button)
                    tuneStates = TuneStates.FINE_TUNE;
                break;

        }
        telemetry.addData("Four Bar Position: ", fourBar.getPosition());
    }
}
