package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.DIFFL_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.DIFFL_PICKUP;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.DIFFR_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.DIFFR_PICKUP;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_PICKUP;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@TeleOp(name="DifferentialTest",group="Tests")
public class DifferentialTest extends OpMode {


    enum TuneStates{
        FINE_TUNE,
        RUN_TO_POSITION
    }
    TuneStates tuneStates = TuneStates.FINE_TUNE;

    Differential diff;
    double posL = 0.5;
    double posR = 0.5;
    @Override
    public void init() {
        diff = new Differential(hardwareMap);
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
                    tuneStates = TuneStates.RUN_TO_POSITION;
                }
                break;
            case RUN_TO_POSITION:




                if(gamepad2.left_stick_button) {
                    tuneStates = TuneStates.RUN_TO_POSITION;
                }
                break;
        }


        telemetry.addData("Left Position: ", posL);
        telemetry.addData("Right Position: ", posR);


    }
}
