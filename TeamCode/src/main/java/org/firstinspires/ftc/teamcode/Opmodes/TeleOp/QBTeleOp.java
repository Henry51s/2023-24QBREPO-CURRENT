package org.firstinspires.ftc.teamcode.Opmodes.TeleOp;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Drive.DriveState.REVERSED;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Commands;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Drive;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Extension;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.FourBar;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.Hardware;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.SideObjective;

//Welcome to Hell
@TeleOp(name="QBTeleOp")
public class QBTeleOp extends OpMode {
    Hardware hw = new Hardware();


    Intake intake;
    Drive drive;
    Extension extendo;


    Gamepad currentGamepad2 = new Gamepad(), previousGamepad2 = new Gamepad();


    Commands commands = new Commands();




    @Override
    public void init() {
        hw.initAll(hardwareMap);
        commands.initCommands(telemetry);
        commands.toInit();
        commands.latchClimbAndDrone();
        intake = hw.intakeInstance;
        drive = hw.driveInstance;
        extendo = hw.extensionInstance;


        intake.setIntakeArmState(Intake.IntakeArmState.GROUND);
        drive.setDriveState(REVERSED);
        extendo.setTargetPosition(0);
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
        drive.loopDrive(gamepad1);
        extendo.loopExtension(gamepad2);


        if(currentGamepad2.left_bumper && !previousGamepad2.left_bumper){
            commands.releasePixels();
        }

        if(currentGamepad2.a && !previousGamepad2.a){
            commands.extendLift(Lift.LiftState.RETRACTED);
        }
        if(currentGamepad2.b && !previousGamepad2.b){
            commands.extendLift(Lift.LiftState.LOW);
        }
        if(currentGamepad2.x && !previousGamepad2.x){
            commands.extendLift(Lift.LiftState.MED);
        }
        if(currentGamepad2.y && !previousGamepad2.y){
            commands.extendLift(Lift.LiftState.HIGH);
        }

        if(currentGamepad2.dpad_up && !previousGamepad2.dpad_up){
            commands.toDeposit();
        }

        if(currentGamepad2.dpad_down && !previousGamepad2.dpad_down){
            //pickup sequence
            commands.toPickup();
        }
        if(gamepad1.back){
            commands.releaseClimbAndDrone(1000);
        }

        /*if(gamepad1.dpad_up){
            extendo.setExtensionState(Extension.ExtensionState.FAR);
        }
        if(gamepad1.dpad_down){
            extendo.setExtensionState(Extension.ExtensionState.RETRACTED);
        }*/

        telemetry.addData("Extendo error: ", commands.getExtensionError());
    }
}
