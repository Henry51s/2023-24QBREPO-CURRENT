package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.xResolution;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.yResolution;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.NonOpmodes.Webcam.ElementDetectionPipeline;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name="VisionProcessorTest")
public class VisionProcessorTest extends LinearOpMode {

    private VisionPortal portal;
    private ElementDetectionPipeline elementDetectionPipeline;

    @Override
    public void runOpMode() throws InterruptedException {

        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(elementDetectionPipeline)
                .setCameraResolution(new android.util.Size(xResolution,yResolution))
                .setCamera(BuiltinCameraDirection.BACK)
                .build();


        waitForStart();
        telemetry.addData("Prop Position", elementDetectionPipeline.getDetectionLocation());
        telemetry.update();                        //Will output prop position on Driver Station Console




    }
}
