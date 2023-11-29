package org.firstinspires.ftc.teamcode.NonOpmodes.Commands.DepositCommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.DepositSubsystem;

public class ClawCommand extends InstantCommand {
    public ClawCommand(DepositSubsystem deposit, DepositSubsystem.ClawState clawState){
        super(
                () -> deposit.setClawState(clawState)
        );
    }
}
