package org.firstinspires.ftc.teamcode.NonOpmodes.Commands.DepositCommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.DepositSubsystem;

public class LiftCommand extends InstantCommand {
    public LiftCommand(DepositSubsystem deposit, DepositSubsystem.LiftState liftState){
        super(
                () -> deposit.setLiftState(liftState)
        );
    }
}
