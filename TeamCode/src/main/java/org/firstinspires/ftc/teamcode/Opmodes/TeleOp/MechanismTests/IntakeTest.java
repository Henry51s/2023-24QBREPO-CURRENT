package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Intake;

@TeleOp(name="IntakeTest", group="Tests")
public class IntakeTest extends OpMode {

    Intake intake;

    enum TuningMode{
        INTAKE,
        INTAKE_ARM
    }
    TuningMode tuningMode = TuningMode.INTAKE;
    @Override
    public void init() {
        intake = new Intake(hardwareMap);
    }

    @Override
    public void loop() {
        switch(tuningMode){
            case INTAKE:
                intake.loopIntake(gamepad1);
                
                break;
            /*case INTAKE_ARM:
                if(gamepad1.a)
                    intake.setIntakeArmState(Intake.IntakeArmState.SECOND);
                if(gamepad1.b)
                    intake.setIntakeArmState(Intake.IntakeArmState.THIRD);
                if(gamepad1.x)
                    intake.setIntakeArmState(Intake.IntakeArmState.FOURTH);
                if(gamepad1.y)
                    intake.setIntakeArmState(Intake.IntakeArmState.FIFTH);
                if(gamepad1.left_stick_button)
                    intake.setIntakeArmState(Intake.IntakeArmState.GROUND);

                if(gamepad1.right_bumper){
                    tuningMode = TuningMode.INTAKE;
                }
                break;*/
        }
        telemetry.addData("Intake State: ", intake.getIntakeState());
        telemetry.addData("IntakeArm State: ", intake.getIntakeArmState());

    }
}
