package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.DIFFL_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.DIFFL_INIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.DIFFL_PICKUP;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.DIFFR_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.DIFFR_INIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.DIFFR_PICKUP;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.FOURBAR_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.FOURBAR_INTERMEDIATE_PTD;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.FOURBAR_PICKUP;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.TuningModes;
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
    TuningModes tuningState = TuningModes.FINE_TUNE;
    Gamepad previousGamepad = new Gamepad();
    Gamepad currentGamepad = new Gamepad();

    ElapsedTime timer = new ElapsedTime();
    boolean isRunning = false;
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
                    tuningState = TuningModes.OPERATIONAL;
                break;
            case OPERATIONAL:
                intake.loopIntake(gamepad1);

                if(currentGamepad.dpad_up && !previousGamepad.dpad_up) {
                    int counter = 0;
                    isRunning = true;
                    timer.reset();
                    fourBar.setFourBarPositionSlow(FOURBAR_DEPOSIT);
                    fourBarPos = FOURBAR_DEPOSIT;
                    while(timer.milliseconds() < 1000){
                        counter ++;
                    }

                        diff.setState(Differential.State.DEPOSIT);
                        isRunning = false;
                    diffPosR = DIFFR_DEPOSIT;
                    diffPosL = DIFFL_DEPOSIT;

                    }





                if(currentGamepad.dpad_right && !previousGamepad.dpad_right){
                    fourBar.setState(FourBar.State.INTERMEDIATE_PTD);
                    fourBarPos = FOURBAR_INTERMEDIATE_PTD;
                    diff.setState(Differential.State.INTERMEDIATE_PTD);
                    diffPosR = DIFFR_INIT;
                    diffPosL = DIFFL_INIT;
                }
                if(currentGamepad.dpad_left && !previousGamepad.dpad_left) {
                    claw.setClawState(Claw.ClawState.OPEN);
                    diff.setState(Differential.State.PICKUP);
                    diffPosR = DIFFR_PICKUP;
                    diffPosL = DIFFL_PICKUP;
                    fourBar.setState(FourBar.State.PICKUP);
                    fourBarPos = FOURBAR_PICKUP;
                }
                if(gamepad1.left_bumper)
                    claw.setClawState(Claw.ClawState.OPEN);
                if(gamepad1.right_bumper)
                    claw.setClawState(Claw.ClawState.CLOSE);

                if(gamepad1.right_stick_button)
                    tuningState = TuningModes.FINE_TUNE;
                break;
        }
        telemetry.addData("FourBar Position: ", fourBar.getPosition());

        telemetry.addData("DiffL Position: ", diff.getDiffPositions()[0]);
        telemetry.addData("DiffR Position: ", diff.getDiffPositions()[1]);

        telemetry.addData("FourBar state: ", fourBar.getState());
        telemetry.addData("Diff state: ", diff.getState());
        telemetry.addData("Claw State: ", claw.getClawState());

    }
}
