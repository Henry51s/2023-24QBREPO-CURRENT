package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.V4B_PICKUP;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@TeleOp(name="v4btest",group="Tests")
public class V4BTest extends OpMode {
    Servo v4bLeft, v4bRight;
    Hardware hardware = new Hardware();
    double pos = 0.5;
    boolean deposit = true;
    boolean pickup = false;
    enum TuneStates{
        FINE_TUNE,
        RUN_TO_POSITION
    }
    TuneStates tuneStates = TuneStates.FINE_TUNE;
    @Override
    public void init() {
        hardware.initDeposit(hardwareMap);
        v4bLeft = hardware.v4bL;
        v4bRight = hardware.v4bR;

    }

    @Override
    public void loop() {
        switch(tuneStates){
            case FINE_TUNE:
                if(gamepad1.a){
                    pos += 0.001;
                }
                if (gamepad1.b){
                    pos -= 0.001;
                }
                v4bLeft.setPosition(pos);
                v4bRight.setPosition(pos);

                if(gamepad1.left_stick_button){
                    tuneStates = TuneStates.RUN_TO_POSITION;
                }
                break;
            case RUN_TO_POSITION:
                if(gamepad2.a){
                    pickup = false;
                    deposit = true;
                }
                if(gamepad2.b){
                    pickup = true;
                    deposit = false;
                }
                if(pickup){
                    v4bLeft.setPosition(V4B_PICKUP);
                    v4bRight.setPosition(V4B_PICKUP);
                }
                else if (deposit){
                    v4bLeft.setPosition(V4B_DEPOSIT);
                    v4bRight.setPosition(V4B_DEPOSIT);
                }
                if(gamepad2.left_stick_button){
                    tuneStates = TuneStates.FINE_TUNE;
                }
                break;

        }


        telemetry.addData("PosR: ", v4bRight.getPosition());
        telemetry.addData("PosL: ", v4bLeft.getPosition());
        telemetry.addData("State: ", tuneStates);
        telemetry.addData("Pickup? ", pickup);
        telemetry.addData("Deposit? ", deposit);

    }
}
