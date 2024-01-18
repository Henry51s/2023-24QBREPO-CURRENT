package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.ElementDetectionPipeline;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.KookyDetectionPipeline;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.Webcam;

@TeleOp(name="VisionProcessorTest")
public class VisionProcessorTest extends LinearOpMode {

    //private VisionPortal portal;
    private ElementDetectionPipeline elementDetectionPipeline = new ElementDetectionPipeline(ElementDetectionPipeline.ElementColor.RED);
    Webcam webcam;

    KookyDetectionPipeline kookyDetectionPipeline = new KookyDetectionPipeline();

    @Override
    public void runOpMode() throws InterruptedException {
        webcam = webcam.getInstance();
        webcam.startWebcam(hardwareMap, ElementDetectionPipeline.ElementColor.RED);
        /*portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, WEBCAM))
                .setCameraResolution(new android.util.Size(xResolution,yResolution))
                .setCamera(BuiltinCameraDirection.BACK)
                .addProcessor(elementDetectionPipeline)
                .build();*/


        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("LeftColor: ", kookyDetectionPipeline.getColorReadings()[0]);
            telemetry.addData("CenterColor: ", kookyDetectionPipeline.getColorReadings()[1]);
            telemetry.update();
            //telemetry.addData("Prop Position", webcam.getDetectionLocation());
            //telemetry.update();                        //Will output prop position on Driver Station Console
        }
    }
}
