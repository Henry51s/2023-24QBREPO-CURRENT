package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.WEBCAM;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.xResolution;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.yResolution;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.NonOpmodes.Webcam.ElementDetectionPipeline;
import org.firstinspires.ftc.vision.VisionPortal;

@TeleOp(name="VisionProcessorTest")
public class VisionProcessorTest extends LinearOpMode {

    private VisionPortal portal;
    private ElementDetectionPipeline elementDetectionPipeline = new ElementDetectionPipeline(ElementDetectionPipeline.ElementColor.RED);

    @Override
    public void runOpMode() throws InterruptedException {


        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, WEBCAM))
                .setCameraResolution(new android.util.Size(xResolution,yResolution))
                .setCamera(BuiltinCameraDirection.BACK)
                .addProcessor(elementDetectionPipeline)
                .build();
        portal.getProcessorEnabled(elementDetectionPipeline);





        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("Prop Position", elementDetectionPipeline.getDetectionLocation());
            telemetry.update();                        //Will output prop position on Driver Station Console
        }





    }
}
