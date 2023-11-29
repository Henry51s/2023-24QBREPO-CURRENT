package org.firstinspires.ftc.teamcode.NonOpmodes.Commands.DepositCommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.DepositSubsystem;

public class V4bCommand extends InstantCommand {
    public V4bCommand(DepositSubsystem deposit, DepositSubsystem.V4bState v4bState){
        super(
                () -> deposit.setV4bState(v4bState)
        );
    }
}
