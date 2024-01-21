package org.firstinspires.ftc.teamcode.Opmodes.TeleOp.MechanismTests;

import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.dashboardStreamFps;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.xResolution;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Globals.GlobalVars.yResolution;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline.Color.BLUE;
import static org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline.Color.RED;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.RegularVisionPipeline;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Config
@TeleOp(name="WebcamDashboardTest",group="Tests")
public class WebcamDashboardTest extends OpMode {
    OpenCvCamera webcam;

    PrimaryDetectionPipeline goodPipeline = new PrimaryDetectionPipeline();

    RegularVisionPipeline regPipeline = new RegularVisionPipeline();

    FtcDashboard dashboard;
    TelemetryPacket packet = new TelemetryPacket();

    @Override
    public void init() {
        goodPipeline.initPipeline(RED);

        int cameraMonitorId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId","id",hardwareMap.appContext.getPackageName());

        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorId);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {

            @Override
            public void onOpened() {
                try {
                    webcam.setPipeline(goodPipeline);
                }
                catch(Exception exception){
                    telemetry.addLine("Error!");
                }
                webcam.startStreaming(xResolution, yResolution, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });

        dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(25);
        dashboard.startCameraStream(webcam, dashboardStreamFps);


    }

    @Override
    public void init_loop(){
        telemetry.addData("Position: ", goodPipeline.getLocation());
        telemetry.addData("Left Position: ", goodPipeline.getLeftTotal());
        telemetry.addData("Center Position: ", goodPipeline.getCenterTotal());

        telemetry.addData("Center Rows: ", goodPipeline.getMatInfo()[0]);
        telemetry.addData("Center Col: ", goodPipeline.getMatInfo()[1]);
        //telemetry.addData("Left Rows: ", goodPipeline.getMatInfo()[2]);
        //telemetry.addData("Left Col: ", goodPipeline.getMatInfo()[3]);
    }

    @Override
    public void loop() {
        telemetry.addData("Position: ", goodPipeline.getLocation());
        telemetry.addData("Left Position: ", goodPipeline.getLeftTotal());
        telemetry.addData("Center Position: ", goodPipeline.getCenterTotal());


        //packet.put("xPos: ", pipeline.getX());
        //packet.put("yPos: ", pipeline.getY());
        //dashboard.sendTelemetryPacket(packet);



    }
}
