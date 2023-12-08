package org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.INTAKE_MAX_POWER;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

public class Intake {
    DcMotor intake;
    Hardware hardware = new Hardware();

    public enum IntakeState{
        STOP,
        NORMAL,
        REVERSED
    }
    IntakeState intakeState = IntakeState.NORMAL;

    public Intake(HardwareMap hw){
        hardware.initIntake(hw);
        intake = hardware.intake;
    }

    public void setIntakeState(IntakeState state) {
        intakeState = state;
        switch(state){
            case STOP:
                intake.setPower(0);
                break;
            case NORMAL:
                intake.setPower(INTAKE_MAX_POWER);
                break;
            case REVERSED:
                intake.setPower(-INTAKE_MAX_POWER);
                break;
        }
    }

    public void loopIntake(Gamepad gamepad){
        if(gamepad.left_bumper){
            setIntakeState(IntakeState.NORMAL);
        }
        if(gamepad.right_bumper){
            setIntakeState(IntakeState.REVERSED);
        }
        else if (!gamepad.right_bumper && !gamepad.left_bumper){
            setIntakeState(IntakeState.STOP);
        }
    }
}
