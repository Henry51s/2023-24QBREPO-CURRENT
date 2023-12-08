package org.firstinspires.ftc.teamcode.Opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Diff;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.FourBar;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Intake;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;
//Welcome to Hell
@TeleOp(name="QBTeleOp")
public class QBTeleOp extends OpMode {
    Hardware hw = new Hardware();

    Claw claw;
    Diff diff;
    FourBar fourBar;
    Lift lift;
    Intake intake;
    @Override
    public void init() {
        hw.initRobot(hardwareMap);
        claw = new Claw(hardwareMap);
        diff = new Diff(hardwareMap);
        fourBar = new FourBar(hardwareMap);
        lift = new Lift(hardwareMap);
        intake = new Intake(hardwareMap);

        claw.setClawState(Claw.ClawState.OPEN);
        diff.setDiffState(Diff.DiffState.DEPOSIT);
        fourBar.setV4bState(FourBar.V4bState.DEPOSIT);
        lift.setLiftState(Lift.LiftState.RETRACTED);
    }

    @Override
    public void loop() {
        /*
        Controls:
        -Claw -> gamepad1 bumpers
        -Differential -> gamepad1 A & B
        -v4b -> gamepad1 X & Y
        -Drive -> gamepad1

        -Intake -> gamepad2 bumpers
         */
        hw.loopDrive(gamepad1);
        intake.loopIntake(gamepad1);
        fourBar.loopFourBar();

        if(gamepad2.left_bumper){
            claw.setClawState(Claw.ClawState.OPEN);
        }
        if(gamepad2.right_bumper){
            claw.setClawState(Claw.ClawState.CLOSE);
        }
        if(gamepad2.x){
            fourBar.setV4bState(FourBar.V4bState.DEPOSIT);
        }
        if(gamepad2.a){
            diff.setDiffState(Diff.DiffState.DEPOSIT);
        }
        if(gamepad2.y){
            diff.setDiffState(Diff.DiffState.PICKUP);
            fourBar.setV4bState(FourBar.V4bState.PICKUP);
        }
        telemetry.addData("fourBar time: ", fourBar.getTime());
        telemetry.addData("Before buffer: ", fourBar.beforeBuffer);
    }
}
