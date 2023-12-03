package org.firstinspires.ftc.teamcode.Opmodes.MechanismTests;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.DIFFL_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.DIFFL_PICKUP;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.DIFFR_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.DIFFR_PICKUP;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_PICKUP;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@TeleOp(name="DifferentialTest")
public class DifferentialTest extends OpMode {
    Servo diffL, diffR, v4bL, v4bR;
    Hardware hardware = new Hardware();
    double posL = 0.5;
    double posR = 0.5;
    boolean pickup = false;
    boolean deposit = true;
    enum TuneStates{
        FINE_TUNE,
        RUN_TO_POSITION
    }
    TuneStates tuneStates = TuneStates.FINE_TUNE;

    @Override
    public void init() {
        hardware.initDeposit(hardwareMap);
        diffL = hardware.diffL;
        diffR = hardware.diffR;

        v4bL = hardware.v4bL;
        v4bR = hardware.v4bR;

        diffL.setPosition(posL);
        diffR.setPosition(posR);

    }

    @Override
    public void loop() {
        switch(tuneStates){
            case FINE_TUNE:

                if(gamepad1.left_bumper){
                    v4bL.setPosition(V4B_PICKUP);
                    v4bR.setPosition(V4B_PICKUP);
                }
                if(gamepad1.right_bumper){
                    v4bL.setPosition(V4B_DEPOSIT);
                    v4bR.setPosition(V4B_DEPOSIT);
                }

                if(gamepad1.a){
                    posL += 0.001;
                }
                if(gamepad1.b){
                    posL -= 0.001;
                }
                if(gamepad1.x){
                    posR += 0.001;
                }
                if(gamepad1.y){
                    posR -= 0.001;
                }

                diffL.setPosition(posL);
                diffR.setPosition(posR);

                if(gamepad1.left_stick_button){
                    tuneStates = TuneStates.RUN_TO_POSITION;
                }
                break;
            case RUN_TO_POSITION:

                if(gamepad2.x){
                    pickup = false;
                    deposit = true;
                }
                if(gamepad2.y){
                    pickup = true;
                    deposit = false;
                }

                if (pickup) {
                    diffL.setPosition(DIFFL_PICKUP);
                    diffR.setPosition(DIFFR_PICKUP);
                    v4bL.setPosition(V4B_PICKUP);
                    v4bR.setPosition(V4B_PICKUP);

                }
                if(deposit){
                    diffL.setPosition(DIFFL_DEPOSIT);
                    diffR.setPosition(DIFFR_DEPOSIT);
                    v4bL.setPosition(V4B_DEPOSIT);
                    v4bR.setPosition(V4B_DEPOSIT);

                }


                if(gamepad2.left_stick_button) {
                    tuneStates = TuneStates.RUN_TO_POSITION;
                }
                break;
        }


        telemetry.addData("secArmL Pos: ", diffL.getPosition());
        telemetry.addData("secArmR Pos: ", diffR.getPosition());
        telemetry.addData("State: ", tuneStates);
        telemetry.addData("Pickup? ", pickup);
        telemetry.addData("Deposit? ", deposit);

    }
}
