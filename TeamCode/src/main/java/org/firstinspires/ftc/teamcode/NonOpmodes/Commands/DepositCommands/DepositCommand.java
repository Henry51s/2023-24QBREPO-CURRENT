package org.firstinspires.ftc.teamcode.NonOpmodes.Commands.DepositCommands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.DepositSubsystem;

public class DepositCommand extends ParallelCommandGroup {

    public DepositCommand(
            DepositSubsystem deposit,
            DepositSubsystem.LiftState liftState,
            DepositSubsystem.V4bState v4bState,
            DepositSubsystem.DiffState diffState,
            DepositSubsystem.ClawState clawState
    ){
        addCommands(
                new V4bCommand(deposit, v4bState),
                new DiffCommand(deposit, diffState),
                new ClawCommand(deposit, clawState),
                new LiftCommand(deposit, liftState)
        );
    }

}
