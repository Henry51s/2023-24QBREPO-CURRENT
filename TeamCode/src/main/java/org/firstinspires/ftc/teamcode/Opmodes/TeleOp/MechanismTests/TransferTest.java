package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.FourBar;

@TeleOp(name="TransferTest")
public class TransferTest extends OpMode {
    Differential diff;
    FourBar fourBar;
    Claw claw;

    double diffPosL = 0.5;
    double diffPosR = 0.5;

    double fourBarPos = 0.5;

    enum TuningState{
        FINE_TUNE,
        OPERATIONAL
    }
    TuningState tuningState = TuningState.FINE_TUNE;

    ElapsedTime v4b_timer = new ElapsedTime();
    @Override
    public void init() {
        diff = new Differential(hardwareMap);
        fourBar = new FourBar(hardwareMap);
        claw = new Claw(hardwareMap);

    }

    @Override
    public void loop() {
        switch(tuningState){
            case FINE_TUNE:
                diff.setDiffLPosition(diffPosL);
                diff.setDiffRPosition(diffPosR);

                fourBar.setV4BPosition(fourBarPos);

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

                if(gamepad1.dpad_up) {
                    fourBar.setV4bState(FourBar.FourBarState.DEPOSIT);
                    diff.setDiffState(Differential.DiffState.DEPOSIT);

                }
                if(gamepad1.dpad_right){
                    fourBar.setV4bState(FourBar.FourBarState.INTERMEDIATE);
                    diff.setDiffState(Differential.DiffState.INTERMEDIATE);
                }
                if(gamepad1.dpad_down) {
                    fourBar.setV4bState(FourBar.FourBarState.PICKUP);
                    diff.setDiffState(Differential.DiffState.PICKUP);
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
        telemetry.addData("Claw Position: ", claw.getClawPosition());

    }
}
