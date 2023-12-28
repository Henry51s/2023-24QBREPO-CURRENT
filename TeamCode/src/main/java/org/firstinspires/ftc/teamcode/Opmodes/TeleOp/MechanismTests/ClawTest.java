package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.CLAW_LATCH;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.CLAW_RELEASE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@TeleOp(name="ClawTest", group="Tests")
public class ClawTest extends OpMode {
    Servo claw;
    Hardware hardware = new Hardware();
    double pos = 0.5;
    enum ClawStates{
        FINE_TUNE,
        OPERATIONAL
    }
    ClawStates clawStates = ClawStates.FINE_TUNE;
    @Override
    public void init() {
        hardware.initDeposit(hardwareMap);
        claw = hardware.claw;
        claw.setPosition(pos);
    }

    @Override
    public void loop() {
        switch(clawStates){
            case FINE_TUNE:
                if(gamepad1.dpad_up){
                    pos += 0.001;
                }
                else if(gamepad1.dpad_down){
                    pos -= 0.001;
                }
                if(pos >= 1){
                    pos = 1;
                }
                else if(pos <= 0){
                    pos = 0;
                }
                claw.setPosition(pos);
                if(gamepad1.left_stick_button){
                    clawStates = ClawStates.OPERATIONAL;
                }
                break;
            case OPERATIONAL:
                if(gamepad1.a){
                    claw.setPosition(CLAW_RELEASE);

                }
                if(gamepad1.b){
                    claw.setPosition(CLAW_LATCH);

                }
                if(gamepad1.right_stick_button){
                    clawStates = ClawStates.FINE_TUNE;
                }
                break;
        }

        telemetry.addData("Claw pos: ", claw.getPosition());
        telemetry.addData("Mode: ", clawStates);



        }
    }
