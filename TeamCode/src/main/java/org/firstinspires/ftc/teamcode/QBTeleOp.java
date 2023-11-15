package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NonOpmodes.Hardware;
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

        hardware.initDrive(hardwareMap);
        frontLeft = hardware.frontLeft;
        frontRight = hardware.frontRight;
        backLeft = hardware.backLeft;
        backRight = hardware.backRight;

        hardware.initIntake(hardwareMap);
        intL = hardware.intL;
        intR = hardware.intR;
        linearL = hardware.linearLeft;
        linearR = hardware.linearRight;
        linearL.setPosition(1);
        linearR.setPosition(1);
    }

    @Override
    public void loop() {

        if(gamepad2.a){
            intL.setPower(0.75);
            intR.setPower(0.75);

        }
        else if(gamepad2.b){
            intL.setPower(-0.75);
            intR.setPower(-0.75);
        }
        else{
            intL.setPower(0);
            intR.setPower(0);
        }


        /*linearL.setPosition(-gamepad2.left_stick_y);
        linearR.setPosition(-gamepad2.left_stick_y);*/
        if(gamepad2.y){
            pos += 0.001;
        }
        if(gamepad2.x){
            pos -= 0.001;
        }
        if(pos <= 0){
            pos = 0;
        }
        if(pos >= 1){
            pos = 1;
        }
        telemetry.addData("Pos: ", pos);
        linearL.setPosition(pos);
        linearR.setPosition(pos);





















        double y = gamepad1.left_stick_y; // Remember, Y stick is reversed!
        double x = -gamepad1.left_stick_x;
        double rx = -gamepad1.right_stick_x;

        double ry = -gamepad1.right_stick_y;

        frontLeft.setPower(y + x + rx);
        backLeft.setPower(y - x + rx);
        frontRight.setPower(y - x - rx);
        backRight.setPower(y + x - rx);

    }}
