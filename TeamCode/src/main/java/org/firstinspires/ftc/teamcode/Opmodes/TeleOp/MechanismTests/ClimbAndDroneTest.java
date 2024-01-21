package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;

@TeleOp(name="ClimbTest")
public class ClimbAndDroneTest extends OpMode {
    Servo climb1, climb2, drone;
    Hardware hw = new Hardware();
    double climbPosition = 0.5, dronePosition = 0.5;
    enum TuningMode{
        CLIMB,
        DRONE
    }
    TuningMode tuningMode = TuningMode.CLIMB;
    @Override
    public void init() {
        hw.initClimbAndDrone(hardwareMap);
        climb1 = hw.climb1;
        climb2 = hw.climb2;
        drone = hw.drone;
    }

    @Override
    public void loop() {
        switch(tuningMode){
            case CLIMB:
                climb1.setPosition(climbPosition);
                climb2.setPosition(climbPosition);
                if(gamepad1.dpad_up){
                    climbPosition += 0.001;
                }
                if(gamepad1.dpad_down){
                    climbPosition -= 0.001;
                }
                if(gamepad1.left_stick_button){
                    tuningMode = TuningMode.DRONE;
                }

                break;
            case DRONE:
                drone.setPosition(dronePosition);
                if(gamepad1.dpad_up){
                    dronePosition += 0.001;
                }
                if(gamepad1.dpad_down){
                    dronePosition -= 0.001;
                }
                if(gamepad1.right_stick_button){
                    tuningMode = TuningMode.CLIMB;
                }

                break;
        }

        telemetry.addData("TuningMode: ", tuningMode);
        telemetry.addData("climbPosition: ", climbPosition);
        telemetry.addData("dronePosition: ", dronePosition);

    }
}
