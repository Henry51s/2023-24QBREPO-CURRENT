package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.FourBar;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake;

@TeleOp(name="TransferTest")
public class TransferTest extends OpMode {
    Differential diff;
    FourBar fourBar;
    Claw claw;
    Intake intake;

    double diffPosL = 0.5;
    double diffPosR = 0.5;
    double fourBarPos = 0.5;

    enum TuningState{
        FINE_TUNE,
        OPERATIONAL
    }
    TuningState tuningState = TuningState.FINE_TUNE;
    Gamepad previousGamepad = new Gamepad();
    Gamepad currentGamepad = new Gamepad();
    @Override
    public void init() {
        diff = Differential.getInstance();
        fourBar = FourBar.getInstance();
        claw = Claw.getInstance();
        intake = Intake.getInstance();

        diff.initDifferential(hardwareMap);
        fourBar.initFourBar(hardwareMap);
        claw.initClaw(hardwareMap);
        intake.initIntake(hardwareMap);
    }

    @Override
    public void loop() {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
        switch(tuningState){
            case FINE_TUNE:
                diff.setDiffLPosition(diffPosL);
                diff.setDiffRPosition(diffPosR);

                fourBar.setFourBarPosition(fourBarPos);

                if(gamepad1.dpad_up){
                    diffPosL += 0.001;
                    diffPosR += 0.001;
                }
                if(gamepad1.dpad_down){
                    diffPosL -= 0.001;
                    diffPosR -= 0.001;
                }
                if(gamepad1.dpad_left){
                    diffPosL += 0.001;
                    diffPosR -= 0.001;
                }
                if(gamepad1.dpad_right){
                    diffPosL -= 0.001;
                    diffPosR += 0.001;
                }

                if(Math.abs(gamepad1.left_stick_y) > 0)
                    fourBarPos += 0.001*Math.signum(gamepad1.left_stick_y);

                if(gamepad1.left_bumper)
                    claw.setClawState(Claw.ClawState.OPEN);

                if(gamepad1.right_bumper)
                    claw.setClawState(Claw.ClawState.CLOSE);


                if(Math.abs(diffPosL) >= 1)
                    diffPosL = Math.signum(diffPosL);
                if(Math.abs(diffPosR) >= 1)
                    diffPosR = Math.signum(diffPosR);
                if(Math.abs(fourBarPos) >= 1)
                    fourBarPos = Math.signum(fourBarPos);
                if(diffPosL <= 0)
                    diffPosL = 0;
                if(diffPosR <= 0)
                    diffPosR = 0;


                if(gamepad1.left_stick_button)
                    tuningState = TuningState.OPERATIONAL;
                break;
            case OPERATIONAL:

                if(currentGamepad.dpad_up && !previousGamepad.dpad_up) {
                    intake.runIntakeSetTime(500);
                    fourBar.setFourBarState(FourBar.FourBarState.DEPOSIT);

                    diff.setDiffState(Differential.DiffState.DEPOSIT);

                }
                if(currentGamepad.dpad_right && !previousGamepad.dpad_right){
                    fourBar.setFourBarState(FourBar.FourBarState.INTERMEDIATE);
                    diff.setDiffState(Differential.DiffState.INTERMEDIATE);
                }
                if(currentGamepad.dpad_left && !previousGamepad.dpad_left) {
                    diff.setDiffState(Differential.DiffState.PICKUP);
                    fourBar.setFourBarState(FourBar.FourBarState.PICKUP);
                }
                if(gamepad1.left_bumper)
                    claw.setClawState(Claw.ClawState.OPEN);
                if(gamepad1.right_bumper)
                    claw.setClawState(Claw.ClawState.CLOSE);

                if(gamepad1.right_stick_button)
                    tuningState = TuningState.FINE_TUNE;
                break;
        }
        telemetry.addData("FourBar Position: ", fourBar.getPosition());

        telemetry.addData("DiffL Position: ", diff.getDiffPositions()[0]);
        telemetry.addData("DiffR Position: ", diff.getDiffPositions()[1]);

        telemetry.addData("FourBar state: ", fourBar.getFourBarState());
        telemetry.addData("Diff state: ", diff.getDiffState());
        telemetry.addData("Claw State: ", claw.getClawState());

    }
}
