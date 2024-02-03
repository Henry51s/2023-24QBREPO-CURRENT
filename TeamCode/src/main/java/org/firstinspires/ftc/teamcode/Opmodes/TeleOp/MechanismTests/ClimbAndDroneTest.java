package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.SideObjective;

@TeleOp(name="ClimbTest")
public class ClimbAndDroneTest extends OpMode {
    double climbPosition = 0.5, dronePosition = 0.5;
    enum Objective {
        CLIMB,
        DRONE
    }

    enum TuningMode{
        FINE_TUNE,
        OPERATIONAL
    }
    TuningMode tuningMode = TuningMode.FINE_TUNE;
    SideObjective sideObjective;

    Objective activeObjective = Objective.CLIMB;

    Gamepad currentGamepad = new Gamepad(), previousGamepad = new Gamepad();
    @Override
    public void init() {
        sideObjective = SideObjective.getInstance();
        sideObjective.initSideQuest(hardwareMap);

    }

    int climbPressedCounter = 0;
    int dronePressedCounter = 0;

    @Override
    public void loop() {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
        switch(tuningMode){
            case FINE_TUNE:
                switch(activeObjective){
                    case CLIMB:
                        sideObjective.setClimbPosition(climbPosition, climbPosition);
                        if(gamepad1.dpad_up){
                            climbPosition += 0.001;
                        }
                        if(gamepad1.dpad_down){
                            climbPosition -= 0.001;
                        }
                        if(gamepad1.left_bumper){
                            activeObjective = Objective.DRONE;
                        }

                        break;
                    case DRONE:
                        sideObjective.setDronePosition(dronePosition);
                        if(gamepad1.dpad_up){
                            dronePosition += 0.001;
                        }
                        if(gamepad1.dpad_down){
                            dronePosition -= 0.001;
                        }
                        if(gamepad1.right_bumper){
                            activeObjective = Objective.CLIMB;
                        }

                        break;
                }
                if(gamepad1.left_stick_button){
                    tuningMode = TuningMode.OPERATIONAL;
                }
                break;
            case OPERATIONAL:

                if(currentGamepad.dpad_up && !previousGamepad.dpad_up){
                    climbPressedCounter += 1;
                }
                if(currentGamepad.dpad_down && !previousGamepad.dpad_up){
                    dronePressedCounter += 1;
                }

                if ((climbPressedCounter % 2 == 1)) {
                    sideObjective.latchClimb();
                } else {
                    sideObjective.releaseClimb();
                }

                if(dronePressedCounter % 2 == 1){
                    sideObjective.latchDrone();
                }
                else{
                    sideObjective.releaseDrone();
                }
                if(gamepad1.right_stick_button){
                    tuningMode = TuningMode.FINE_TUNE;

                }                break;
        }

        telemetry.addData("TuningMode: ", activeObjective);
        telemetry.addData("climbPosition: ", climbPosition);
        telemetry.addData("dronePosition: ", dronePosition);

    }
}
