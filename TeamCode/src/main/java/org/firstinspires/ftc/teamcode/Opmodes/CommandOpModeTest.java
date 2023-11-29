package org.firstinspires.ftc.teamcode.Opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;
//import photoncore

public class CommandOpModeTest extends CommandOpMode {
    Hardware hw = new Hardware();
    GamepadEx gamepadEx1, gamepadEx2;


    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();

        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        hw.initRobot(hardwareMap);



    }
    @Override
    public void run(){
        hw.loopRobot();
    }
}
