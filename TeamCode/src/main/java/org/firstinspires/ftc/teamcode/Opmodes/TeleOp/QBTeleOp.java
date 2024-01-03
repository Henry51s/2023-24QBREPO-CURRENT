package org.firstinspires.ftc.teamcode.Opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.FourBar;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Intake;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;
//Welcome to Hell
@TeleOp(name="QBTeleOp")
public class QBTeleOp extends OpMode {
    Hardware hw = new Hardware();

    Claw claw;
    Differential differential;
    FourBar fourBar;
    Lift lift;
    Intake intake;

    @Override
    public void init() {
        hw.initRobot(hardwareMap);
        claw = new Claw(hardwareMap);
        differential = new Differential(hardwareMap);
        fourBar = new FourBar(hardwareMap);
        lift = new Lift(hardwareMap);
        intake = new Intake(hardwareMap);

        claw.setClawState(Claw.ClawState.CLOSE);
        differential.setDiffState(Differential.DiffState.DEPOSIT);
        fourBar.setV4bState(FourBar.FourBarState.INIT);
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


        if(gamepad2.left_bumper){
            claw.setClawState(Claw.ClawState.OPEN);
        }
        if(gamepad2.right_bumper){
            claw.setClawState(Claw.ClawState.CLOSE);
        }
        if(gamepad2.x){
            fourBar.setV4bState(FourBar.FourBarState.DEPOSIT);
        }
        if(gamepad2.a){
            differential.setDiffState(Differential.DiffState.DEPOSIT);
        }

        if(gamepad2.y){
            fourBar.setV4bState(FourBar.FourBarState.PICKUP);
            differential.setDiffState(Differential.DiffState.PICKUP);
        }
        if(gamepad2.dpad_left){
            lift.setLiftState(Lift.LiftState.LOW);
        }
        if(gamepad2.dpad_up){
            lift.setLiftState(Lift.LiftState.MED);
        }
        if(gamepad2.dpad_down){
            lift.setLiftState(Lift.LiftState.RETRACTED);
        }

    }
}
