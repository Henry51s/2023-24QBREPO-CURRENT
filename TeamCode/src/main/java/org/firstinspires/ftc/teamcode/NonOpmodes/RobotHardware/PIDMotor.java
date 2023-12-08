package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class PIDMotor {

    private DcMotorEx motor;

    private double kP = 0;
    private double kI = 0;
    private double kD = 0;
    private double kF = 0;

    private double liftFactor = 0.30;

    public double targetPosition = 0; //    Default/starting position




    PIDFController pidf = new PIDFController(kP, kI, kD, kF);

    public PIDMotor(HardwareMap hardwareMap, String configName){
        this.motor = hardwareMap.get(DcMotorEx.class, configName);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //Might add runModes to this constructor idk
    }

    public void setTargetPosition(double targetPosition){
        this.targetPosition = targetPosition;
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

    /*public void setPower(double power){
        motor.setPower(power);
    }*/
    public void setVelocity(double velocity){
    motor.setVelocity(velocity);
    }

    private double output(){
        double output = pidf.calculate(
                motor.getCurrentPosition(), targetPosition
        );
        return output;
    }

    public void loopPID(){
        double currentPos = motor.getCurrentPosition();
        if (currentPos != targetPosition){
            setVelocity(output());
        }
    }

}
