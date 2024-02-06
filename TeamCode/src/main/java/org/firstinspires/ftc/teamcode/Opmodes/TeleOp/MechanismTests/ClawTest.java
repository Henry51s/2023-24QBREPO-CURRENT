package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Claw;

@TeleOp(name="ClawTest", group="Tests")
public class ClawTest extends OpMode {

    Claw claw;
    double pos = 0.5;
   TuningModes tuningModes = TuningModes.FINE_TUNE;

    @Override
    public void init() {
        claw = Claw.getInstance();
        claw.initClaw(hardwareMap);
    }

    @Override
    public void loop() {
        switch(tuningModes){
            case FINE_TUNE:
                claw.setClawPosition(pos);
                if(gamepad1.dpad_up){
                    pos += 0.001;
                }
                else if(gamepad1.dpad_down){
                    pos -= 0.001;
                }
                if(pos >= 1){
                    pos = 1;
                }
                else if(pos <= 0) {
                    pos = 0;
                }
                if(gamepad1.left_stick_button){
                    tuningModes = tuningModes.OPERATIONAL;
                }
                break;
            case OPERATIONAL:
                if(gamepad1.a){
                    claw.setClawState(Claw.ClawState.OPEN);
                }
                if(gamepad1.b){
                    claw.setClawState(Claw.ClawState.CLOSE);

                }
                if(gamepad1.right_stick_button){
                    tuningModes = tuningModes.FINE_TUNE;
                }
                break;
        }

        telemetry.addData("Claw pos: ", claw.getClawPosition());
        telemetry.addData("Claw State: ", claw.getClawState());
        }
    }
