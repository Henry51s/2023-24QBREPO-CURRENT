package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Mechanisms.Webcam.PrimaryDetectionPipeline;
import org.firstinspires.ftc.teamcode.NonOpmodes.Enums.AutoLocation;
import org.firstinspires.ftc.teamcode.Opmodes.auto.Bases.LongAutoBase;
import org.firstinspires.ftc.teamcode.Opmodes.auto.Bases.ShortAutoBase;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="BlueLong")
public class BlueLong extends LongAutoBase {


    @Override
    public void init() {
        super.init(AutoLocation.BLUE_LONG,PrimaryDetectionPipeline.Color.BLUE);
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
