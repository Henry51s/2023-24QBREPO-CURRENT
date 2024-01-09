package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 30, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, -61, Math.toRadians(270)))

                                .setReversed(true)
                                .splineTo(new Vector2d(-35,-12), Math.toRadians(90))
                                .setReversed(false)
                                .turn(Math.toRadians(180))
                                .back(3)
                                // deposit purple (you may have to move back slightly)
                                .waitSeconds(2)
                                .splineTo(new Vector2d(0,-14), Math.toRadians(0))
                                .splineTo(new Vector2d(28,-14), Math.toRadians(0))
                                .splineTo(new Vector2d(48,-35), Math.toRadians(0))
                                // deposit yellow
                                .waitSeconds(2)
                                // parked
                                .strafeRight(24)
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}