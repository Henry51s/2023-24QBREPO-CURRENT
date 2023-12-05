package org.firstinspires.ftc.teamcode.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.DepositSubsystem;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;
//Welcome to Hell
@TeleOp(name="QBTeleOp")
public class QBTeleOp extends OpMode {
    /*
    The Plan:
    -2 drivers. One controls drive, the other controls scoring
    -Preset positions for v4b and differential and slide. No fine control
    -A bunch of if statements should work fine
    -Treat the pair of servos powering the diff and v4b as one entity. Both should move in the same direction
     */

    DcMotor frontLeft, frontRight, backLeft,backRight,slide;
    Servo secArmL, secArmR, primArmL, primArmR, clawL, clawR,
    linearL, linearR
    ;
    CRServo intL, intR;
    Hardware hardware = new Hardware();
    double pos = 0.5;

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }}
