package org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam;

import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;

public class RegularVisionPipeline extends OpenCvPipeline {
    @Override
    public Mat processFrame(Mat input) {
        return input;
    }
}
