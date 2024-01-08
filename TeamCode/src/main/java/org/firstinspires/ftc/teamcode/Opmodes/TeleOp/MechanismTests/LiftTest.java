package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;


import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.CHMOTOR_2;
import static org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests.LiftTestConstants.POWER;
import static org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests.LiftTestConstants.TARGET;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@Config
@TeleOp(name="LiftTest",group="Tests")
public class LiftTest extends OpMode {

    //max is 1781
    //Lift lift;
    FtcDashboard dashboard;
    TelemetryPacket packet;

    DcMotorEx lift;

    enum TuningMode{
        FINE_TUNE,
        OPERATIONAL
    }
    TuningMode tuningMode = TuningMode.FINE_TUNE;

    @Override
    public void init() {
        //lift = new Lift(hardwareMap);
        lift = hardwareMap.get(DcMotorEx.class, CHMOTOR_2);;

        dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(25);
        packet = new TelemetryPacket();

    }

    @Override
    public void loop() {
        lift.setPower(gamepad1.left_stick_y*0.5);
        /*switch(tuningMode){
            case FINE_TUNE:
                lift.setPower(gamepad1.left_stick_y*0.5);

                if(gamepad1.left_stick_button)
                    lift.setTargetPosition(lift.getCurrentPosition());
                    tuningMode = TuningMode.OPERATIONAL;
                break;
            case OPERATIONAL:
                lift.loopLift();

                if(gamepad1.a)
                    lift.setLiftState(Lift.LiftState.RETRACTED);
                if(gamepad1.b)
                    lift.setLiftState(Lift.LiftState.LOW);
                if(gamepad1.x)
                    lift.setLiftState(Lift.LiftState.MED);
                if(gamepad1.y)
                    lift.setLiftState(Lift.LiftState.HIGH);


                if(gamepad1.right_stick_button)
                    tuningMode = TuningMode.FINE_TUNE;
                break;
        }*/

        telemetry.addData("Lift Position: ", lift.getCurrentPosition());
    }
}
