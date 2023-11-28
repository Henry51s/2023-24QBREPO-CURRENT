package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.DepositSubsystem;

public class DepositMedCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final DepositSubsystem deposit;
    public DepositMedCommand(DepositSubsystem depositSubsystem){
        deposit = depositSubsystem;
        addRequirements(depositSubsystem);
    }
    @Override
    public void initialize(){
        //Sets lift height, v4b and diff position
        deposit.setLiftState(DepositSubsystem.LiftState.LOW);
        deposit.setV4bState(DepositSubsystem.V4bState.DEPOSIT);
        deposit.setDiffState(DepositSubsystem.DiffState.DEPOSIT);
    }

    @Override
    public void execute(){
        deposit.loop();//Might move this loop function to an outside loop
    }
    //End function = claw, v4b, lift go to deposit position
    //ifFinished = if claw is open or lift is retracted, return true
}
