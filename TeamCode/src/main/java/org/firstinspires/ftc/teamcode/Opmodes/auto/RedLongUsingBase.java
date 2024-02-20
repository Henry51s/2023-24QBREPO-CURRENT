package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoLocation;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline;
import org.firstinspires.ftc.teamcode.Opmodes.auto.Bases.LongAutoBase;
import org.firstinspires.ftc.teamcode.Opmodes.auto.Bases.ShortAutoBase;

@Autonomous(name="RedLongUsingBase")
public class RedLongUsingBase extends LongAutoBase {
    @Override
    public void init() {
        super.init(AutoLocation.RED_LONG, PrimaryDetectionPipeline.Color.RED);
    }
    @Override
    public void init_loop(){
        super.init_loop();

    }
    @Override
    public void loop(){
        super.loop();
    }
    @Override
    public void stop(){
        super.stop();
    }
}
