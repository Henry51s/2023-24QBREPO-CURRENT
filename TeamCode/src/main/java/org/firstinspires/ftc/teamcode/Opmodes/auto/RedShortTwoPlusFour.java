package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoLocation;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline;
import org.firstinspires.ftc.teamcode.Opmodes.auto.Bases.ShortAutoBase;


@Autonomous(name="RedShortTwoPlusFour")
public class RedShortTwoPlusFour extends ShortAutoBase
{
    @Override
    public void init() {
        super.init(AutoLocation.RED_SHORT,PrimaryDetectionPipeline.Color.RED, 1);
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
