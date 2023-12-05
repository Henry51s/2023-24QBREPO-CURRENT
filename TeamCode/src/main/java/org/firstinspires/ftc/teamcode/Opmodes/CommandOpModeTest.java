package org.firstinspires.ftc.teamcode.Opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.Commands.DepositCommands.ClawCommand;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.DepositSubsystem;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;
//import photoncore

@TeleOp(name="CommandOpModeTest")
public class CommandOpModeTest extends CommandOpMode {
    Hardware hw = new Hardware();
    GamepadEx gamepadEx1, gamepadEx2;

    DepositSubsystem depositSubsystem;
    ClawCommand clawCommandOpen;


    @Override
    public void initialize() {
        hw.initRobot(hardwareMap);
        CommandScheduler.getInstance().reset();
        depositSubsystem = new DepositSubsystem(hardwareMap);
        clawCommandOpen = new ClawCommand(depositSubsystem, DepositSubsystem.ClawState.OPEN);

        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        gamepadEx1.getGamepadButton(GamepadKeys.Button.A).whenPressed(() -> schedule(clawCommandOpen));

        hw.initRobot(hardwareMap);



    }
    @Override
    public void run(){
        //hw.loopRobot();
        hw.loopDrive(gamepad1);
    }
}
