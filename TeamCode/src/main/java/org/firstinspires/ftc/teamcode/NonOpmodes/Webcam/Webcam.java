package org.firstinspires.ftc.teamcode.NonOpmodes.Webcam;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.WEBCAM;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.xResolution;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.GlobalVars.yResolution;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

public class Webcam {
    private VisionPortal portal;
    private ElementDetectionPipeline elementDetectionPipeline;

    public Webcam(HardwareMap hw, ElementDetectionPipeline.ElementColor elementColor){
        elementDetectionPipeline.elementColor = elementColor;
        portal = new VisionPortal.Builder()
                .setCamera(hw.get(WebcamName.class, WEBCAM))
                .setCameraResolution(new Size(xResolution,yResolution))
                .setCamera(BuiltinCameraDirection.BACK)
                .addProcessor(elementDetectionPipeline)
                .build();

    }
    public ElementDetectionPipeline.DetectionLocation getDetectionLocation(){
        return elementDetectionPipeline.getDetectionLocation();
    }
}
