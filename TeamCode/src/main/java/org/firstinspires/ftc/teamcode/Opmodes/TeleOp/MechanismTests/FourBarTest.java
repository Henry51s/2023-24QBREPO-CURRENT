package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.Arrays;


import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.TuningModes;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.FourBar;

@TeleOp(name="FourBarTest",group="Tests")
public class FourBarTest extends OpMode {

    TuningModes tuneStates = TuningModes.FINE_TUNE;

    FourBar fourBar;

    Gamepad currentGamepad = new Gamepad();
    Gamepad previousGamepad = new Gamepad();

    double position = 0.5;
    @Override
    public void init() {
        fourBar = FourBar.getInstance();
        fourBar.initFourBar(hardwareMap);
    }

    @Override
    public void loop() {

        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);

        switch(tuneStates){
            case FINE_TUNE:
                if(Math.abs(gamepad1.left_stick_y)>0){
                    position += 0.001*Math.signum(gamepad1.left_stick_y);
                }
                if(Math.abs(position) >= 1)
                    position = Math.signum(position);

                if(currentGamepad.a && !previousGamepad.a)
                    fourBar.setFourBarPositionSlow(0.65);
                if(gamepad1.b)
                    fourBar.setFourBarPosition(position);

                if(gamepad1.left_stick_button)
                    tuneStates = TuningModes.OPERATIONAL;
                break;
            case OPERATIONAL:

                if(gamepad2.right_stick_button)
                    tuneStates = TuningModes.FINE_TUNE;
                break;

        }
        telemetry.addData("Four Bar Position: ", fourBar.getPosition());
        telemetry.addData("Intermediate Positions: ", Arrays.toString(fourBar.intermediatePositions));

    }
}
