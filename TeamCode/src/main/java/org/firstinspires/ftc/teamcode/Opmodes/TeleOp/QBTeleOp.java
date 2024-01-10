package org.firstinspires.ftc.teamcode.Opmodes.TeleOp;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.FOURBAR_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.FOURBAR_PICKUP;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Drive;
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
    Drive drive;

    Gamepad currentGamepad2 = new Gamepad(), previousGamepad2 = new Gamepad();

    double offset = 0.135;
    int n = 0;

    @Override
    public void init() {
        hw.initRobot(hardwareMap);
        claw = new Claw(hardwareMap);
        differential = new Differential(hardwareMap);
        fourBar = new FourBar(hardwareMap);
        intake = new Intake(hardwareMap);
        drive = new Drive(hardwareMap)

        claw.setClawState(Claw.ClawState.CLOSE);
        differential.setDiffState(Differential.DiffState.DEPOSIT);
        fourBar.setFourBarState(FourBar.FourBarState.DEPOSIT);
        intake.setIntakeArmState(Intake.IntakeArmState.GROUND);
    }

    @Override
    public void loop() {
        /*
        Controls:
        -Claw -> gamepad2 bumpers
        -Deposit sequence -> gamepad 2 dpad up
        -Pickup sequence -> gamepad 2 dpad down
        -Intake -> gamepad1 bumpers
         */
        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);

        drive.loopDrive(gamepad1);
        intake.loopIntake(gamepad1);

        if(gamepad2.left_bumper)
            claw.setClawState(Claw.ClawState.OPEN);
        if(gamepad2.right_bumper)
            claw.setClawState(Claw.ClawState.CLOSE);
        if(currentGamepad2.dpad_up && !previousGamepad2.dpad_up){
            //Deposit sequence
            intake.runIntakeSetTime(500);
            fourBar.setFourBarPositionSlow(FOURBAR_DEPOSIT);
            differential.setDiffState(Differential.DiffState.DEPOSIT);

            differential.setOffset(offset*n);
            if(gamepad1.dpad_left && !previousGamepad2.dpad_left){
                n++;
            }
            if(gamepad1.dpad_right && !previousGamepad2.dpad_right){
                n--;
            }
        }
        if(currentGamepad2.dpad_down && !previousGamepad2.dpad_down){
            n = 0;
            //pickup sequence
            differential.setDiffState(Differential.DiffState.PICKUP);
            fourBar.setFourBarPositionSlow(FOURBAR_PICKUP);
        }



    }
}
