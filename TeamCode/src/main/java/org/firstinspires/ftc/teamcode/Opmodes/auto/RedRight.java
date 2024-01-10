package org.firstinspires.ftc.teamcode.Opmodes.auto;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Claw;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Differential;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.FourBar;
import org.firstinspires.ftc.teamcode.NonOpmodes.ProcrastinationCode.Lift;
import org.firstinspires.ftc.teamcode.NonOpmodes.Roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.NonOpmodes.RobotHardware.Hardware;

@Disabled
@Autonomous(name="RedRight")
public class RedRight extends LinearOpMode {

    Hardware hardware = new Hardware();
    Claw claw;
    Differential differential;
    FourBar fourBar;
    Lift lift;

    AutoTrajectories autoTrajectories;
    Trajectory toScore;
    ElapsedTime time = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        //AutoTrajectories autoTrajectories;

        claw = new Claw(hardwareMap);
        differential = new Differential(hardwareMap);
        fourBar = new FourBar(hardwareMap);
        lift = new Lift(hardwareMap);

        claw.setClawState(Claw.ClawState.CLOSE);
        differential.setDiffState(Differential.DiffState.DEPOSIT);
        fourBar.setFourBarState(FourBar.FourBarState.INIT);
        lift.setLiftState(Lift.LiftState.RETRACTED);

        autoTrajectories = new AutoTrajectories(hardwareMap, AutoTrajectories.AutoLocation.RED_RIGHT);


        waitForStart();
        if(isStopRequested()){
            return;
        }

        drive.followTrajectory(toScore);
        lift.setLiftState(Lift.LiftState.LOW);
        fourBar.setFourBarState(FourBar.FourBarState.DEPOSIT);
        claw.setClawState(Claw.ClawState.OPEN);
        time.reset();


        while(opModeIsActive()){
            telemetry.addData("Pose", drive.getPoseEstimate());
            if(time.milliseconds() > 7000){
                fourBar.setFourBarState(FourBar.FourBarState.INIT);
            }
        }
    }
}
