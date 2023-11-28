package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class PIDMotor {

    private DcMotor motor;

    private double kP = 0;
    private double kI = 0;
    private double kD = 0;
    private double kF = 0;

    public double targetPosition = 0; //    Default/starting position

    PIDFController pidf = new PIDFController(kP, kI, kD, kF);

    public PIDMotor(HardwareMap hardwareMap, String configName){
        this.motor = hardwareMap.get(DcMotor.class, configName);
        //Might add runModes to this constructor idk
    }

    public void setTargetPosition(double target){
        this.targetPosition = target;
    }

    public void setCoefficients(double kP, double kI, double kD, double kF){
        pidf.setPIDF(kP, kI, kD, kF);
    }

    public void setDirection(DcMotorSimple.Direction direction){
        motor.setDirection(direction);
    }

    public double getCurrentPosition(){
        return motor.getCurrentPosition();
    }

    public void setPower(double power){
        motor.setPower(power);
    }

    private double output(){
        double output = pidf.calculate(
                motor.getCurrentPosition(), this.targetPosition
        );
        return output;
    }

    public void runToPos(){
        double currentPos = motor.getCurrentPosition();
        if (currentPos != this.targetPosition){
            setPower(output());
        }
    }

}
