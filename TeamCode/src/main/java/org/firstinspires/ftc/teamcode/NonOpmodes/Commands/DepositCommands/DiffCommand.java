package org.firstinspires.ftc.teamcode.NonOpmodes.Commands.DepositCommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.DepositSubsystem;

public class DiffCommand extends InstantCommand {
    public DiffCommand(DepositSubsystem deposit, DepositSubsystem.DiffState diffState){
        super(
                () -> deposit.setDiffState(diffState)
        );
    }
}
