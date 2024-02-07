package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.FourBarDifferentialStates;
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.TuningModes;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Differential;

@TeleOp(name="DifferentialTest",group="Tests")
public class DifferentialTest extends OpMode {



    TuningModes tuneStates = TuningModes.FINE_TUNE;

    Differential diff;
    double posL = 0.5;
    double posR = 0.5;

    public double offset = 0.135;
    double n = 0;

    Gamepad currentGamepad1, previousGamepad1;


    @Override
    public void init() {
        diff = Differential.getInstance();
        diff.initDifferential(hardwareMap);
        previousGamepad1 = new Gamepad();
        currentGamepad1 = new Gamepad();
    }

    @Override
    public void loop() {
        switch(tuneStates){
            case FINE_TUNE:
                diff.setDiffLPosition(posL);
                diff.setDiffRPosition(posR);

                /*if(Math.abs(gamepad1.left_stick_y) > 0){
                    posL += gamepad1.left_stick_y*0.01;
                }
                if(Math.abs(gamepad1.right_stick_y) > 0){
                    posR += gamepad1.right_stick_y*0.01;
                }*/

                if(gamepad1.dpad_up){
                    posL += 0.001;
                    posR += 0.001;
                }
                if(gamepad1.dpad_down){
                    posL -= 0.001;
                    posR -= 0.001;
                }
                if(gamepad1.dpad_left){
                    posL += 0.001;
                    posR -= 0.001;
                }
                if(gamepad1.dpad_right){
                    posL -= 0.001;
                    posR += 0.001;
                }

                if(Math.abs(posL) >= 1){
                    posL = Math.signum(posL);
                }
                if(Math.abs(posR) >= 1){
                    posR = Math.signum(posR);
                }

                if(gamepad1.left_stick_button){
                    tuneStates = tuneStates.OPERATIONAL;
                }
                break;
            case OPERATIONAL:
                previousGamepad1.copy(currentGamepad1);
                currentGamepad1.copy(gamepad1);
                diff.setState(FourBarDifferentialStates.DEPOSIT);
                if(gamepad1.dpad_left && !previousGamepad1.dpad_left){
                    n++;
                }
                if(gamepad1.dpad_right && !previousGamepad1.dpad_right){
                    n--;
                }






                if(gamepad2.right_stick_button) {
                    tuneStates = tuneStates.FINE_TUNE;
                }
                break;
        }


        telemetry.addData("Left Position: ", diff.getDiffPositions()[0]);
        telemetry.addData("Right Position: ", diff.getDiffPositions()[1]);
        telemetry.addData("n: ", n);

    }
}
