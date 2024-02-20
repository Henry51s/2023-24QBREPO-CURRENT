package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline;
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoLocation;
import org.firstinspires.ftc.teamcode.Opmodes.auto.Bases.ShortAutoBase;

@Disabled
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="BlueShort")
public class BlueShort extends ShortAutoBase {


    @Override
    public void init() {
        super.init(AutoLocation.RED_SHORT,PrimaryDetectionPipeline.Color.RED);
    }
    @Override
    public void init_loop() {
        super.init_loop();
    }

    @Override
    public void loop() {
        super.loop();

    }
    @Override
    public void stop(){
        super.stop();
    }
}
