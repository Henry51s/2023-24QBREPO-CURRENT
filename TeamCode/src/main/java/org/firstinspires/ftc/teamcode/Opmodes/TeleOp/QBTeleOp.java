package org.firstinspires.ftc.teamcode.Opmodes.TeleOp;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.FOURBAR_DEPOSIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.FOURBAR_INIT;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.FOURBAR_PICKUP;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.climbLatch;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.climbRelease;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.extendoClimb;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Drive.DriveState.REVERSED;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Drive;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.FourBar;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;
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
    Extension extendo;

    Servo climb1, climb2;

    Gamepad currentGamepad2 = new Gamepad(), previousGamepad2 = new Gamepad();

    DcMotor frontLeft, frontRight, backLeft, backRight;



    @Override
    public void init() {
        hw.initAll(hardwareMap);
        claw = hw.clawInstance;
        differential = hw.differentialInstance;
        fourBar = hw.fourBarInstance;
        intake = hw.intakeInstance;
        drive = hw.driveInstance;
        lift = hw.liftInstance;
        extendo = hw.extensionInstance;

        claw.setClawState(Claw.ClawState.CLOSE);
        differential.setDiffState(Differential.DiffState.INIT);
        fourBar.setFourBarPosition(FOURBAR_INIT);
        intake.setIntakeArmState(Intake.IntakeArmState.GROUND);
        drive.setDriveState(REVERSED);
        extendo.setTargetPosition(0);

        hw.initClimb(hardwareMap);
        climb1 = hw.climb1;
        climb2 = hw.climb2;
        climb1.setPosition(climbLatch);
        climb2.setPosition(climbLatch);

        hw.initDrive(hardwareMap);

        frontLeft = hw.frontLeft;
        frontRight = hw.frontRight;
        backLeft = hw.backLeft;
        backRight = hw.backRight;
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

        intake.loopIntake(gamepad1);
        //differential.loopDifferential(gamepad2);

        if(gamepad2.left_bumper)
            claw.setClawState(Claw.ClawState.OPEN);
        if(gamepad2.right_bumper)
            claw.setClawState(Claw.ClawState.CLOSE);
        if(currentGamepad2.dpad_up && !previousGamepad2.dpad_up){
            //Deposit sequence
            fourBar.setFourBarPositionSlow(FOURBAR_DEPOSIT);

        }
        if(currentGamepad2.right_stick_button && !previousGamepad2.right_stick_button){
            differential.setDiffState(Differential.DiffState.DEPOSIT);
        }
        if(currentGamepad2.dpad_down && !previousGamepad2.dpad_down){
            //pickup sequence
            claw.setClawState(Claw.ClawState.OPEN);
            differential.setDiffState(Differential.DiffState.PICKUP);
            fourBar.setFourBarPositionSlow(FOURBAR_PICKUP);
        }
        if(currentGamepad2.a && !previousGamepad2.a){
            lift.setLiftState(Lift.LiftState.RETRACTED);
        }
        if(currentGamepad2.b && !previousGamepad2.b){
            lift.setLiftState(Lift.LiftState.LOW);
        }
        if(gamepad1.dpad_left){
            climb1.setPosition(climbRelease);
            climb2.setPosition(climbRelease);
        }
        if(gamepad1.dpad_right){
            extendo.setTargetPosition(extendoClimb);
        }




        double y = -gamepad1.left_stick_y; // Remember, Y stick is reversed!
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x*0.5;



        frontLeft.setPower(y + x + rx);
        backLeft.setPower(y - x + rx);
        frontRight.setPower(y - x - rx);
        backRight.setPower(y + x - rx);
    }
}
