package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.ElementDetectionPipeline;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.Webcam;

@TeleOp(name="VisionProcessorTest")
public class VisionProcessorTest extends LinearOpMode {

    //private VisionPortal portal;
    //private ElementDetectionPipeline elementDetectionPipeline = new ElementDetectionPipeline(ElementDetectionPipeline.ElementColor.RED);
    Webcam webcam;

    @Override
    public void runOpMode() throws InterruptedException {
        webcam = new Webcam(hardwareMap, ElementDetectionPipeline.ElementColor.RED);
        /*portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, WEBCAM))
                .setCameraResolution(new android.util.Size(xResolution,yResolution))
                .setCamera(BuiltinCameraDirection.BACK)
                .addProcessor(elementDetectionPipeline)
                .build();*/


        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("Prop Position", webcam.getDetectionLocation());
            telemetry.update();                        //Will output prop position on Driver Station Console
        }





    }
}
