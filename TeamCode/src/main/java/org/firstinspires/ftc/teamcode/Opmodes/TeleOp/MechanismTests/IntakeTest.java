package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake;

@TeleOp(name="IntakeTest", group="Tests")
public class IntakeTest extends OpMode {

    Intake intake;


    double pos = 0.5;
    TuningModes tuningMode = TuningModes.FINE_TUNE;

    Gamepad current = new Gamepad(), previous = new Gamepad();
    @Override
    public void init() {
        intake = Intake.getInstance();
        intake.initIntake(hardwareMap);
        intake.intakeArm.setPosition(pos);
    }

    @Override
    public void loop() {
        previous.copy(current);
        current.copy(gamepad1);

        switch(tuningMode){

            case FINE_TUNE:
                intake.loopIntake(gamepad1);

                if(gamepad1.dpad_up){
                    pos += 0.001;
                }
                if(gamepad1.dpad_down){
                    pos -= 0.001;
                }
                if(pos >= 1){
                    pos = 1;
                }
                if(pos <= 0){
                    pos = 0;
                }
                intake.setArmPosition(pos);

                if(gamepad1.left_stick_button)
                    tuningMode = TuningModes.OPERATIONAL;
                break;
            case OPERATIONAL:
                if(gamepad1.start)
                    intake.setIntakeArmState(Intake.IntakeArmState.GROUND);
                if(gamepad1.a)
                    intake.setIntakeArmState(Intake.IntakeArmState.SECOND);
                if(gamepad1.b)
                    intake.setIntakeArmState(Intake.IntakeArmState.THIRD);
                if(gamepad1.x)
                    intake.setIntakeArmState(Intake.IntakeArmState.FOURTH);
                if(gamepad1.y)
                    intake.setIntakeArmState(Intake.IntakeArmState.FIFTH);

                if(current.left_bumper && !previous.left_bumper){
                    intake.runIntakeSetTime(5, true, 0.5);
                }

                if(gamepad1.right_stick_button)
                    tuningMode = TuningModes.FINE_TUNE;
                break;
        }

        telemetry.addData("Tuning Mode: ", tuningMode);
        telemetry.addData("Intake State: ", intake.getIntakeState());
        telemetry.addData("IntakeArm State: ", intake.getIntakeArmState());

        telemetry.addData("Intake Arm Position: ", intake.getIntakeArmPosition());
        telemetry.addData("Intake Motor Position: ", intake.getIntakePosition());
        telemetry.addData("Target Position: ", intake.targetPosition);
        telemetry.addData("Roll Counter: ", intake.roundedRollCounter);

    }
}
