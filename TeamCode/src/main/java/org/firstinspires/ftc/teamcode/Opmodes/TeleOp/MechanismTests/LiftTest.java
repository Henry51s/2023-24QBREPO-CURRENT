package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Lift;

@Config
@TeleOp(name="LiftTest",group="Tests")
public class LiftTest extends OpMode {

    //max is 1781
    Lift lift;
    FtcDashboard dashboard;
    TelemetryPacket packet;

    public static int targetPosition = 0;


    @Override
    public void init() {
        lift = Lift.getInstance();
        lift.initLift(hardwareMap, false);


        dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(25);
        packet = new TelemetryPacket();

    }

    @Override
    public void loop() {

                if(gamepad1.a)
                    lift.setLiftState(Lift.LiftState.RETRACTED);
                if(gamepad1.b)
                    lift.setLiftState(Lift.LiftState.LOW);
                if(gamepad1.x)
                    lift.setLiftState(Lift.LiftState.MED);
                if(gamepad1.y)
                    lift.setLiftState(Lift.LiftState.HIGH);

                telemetry.addData("Lift Position: ", lift.getCurrentPosition());
                telemetry.addData("Lift State: ", lift.getLiftState());

                packet.put("Current Position: ", lift.getCurrentPosition());
                packet.put("Target Position: ", lift.getTargetPosition());
                packet.put("Error: ", lift.getTargetPosition() - lift.getCurrentPosition());
                dashboard.sendTelemetryPacket(packet);
        }


    }
