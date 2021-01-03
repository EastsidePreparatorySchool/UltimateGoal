package org.eastsideprep.ftc.teamcode.null8103;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.eastsideprep.ftc.teamcode.null8103.drive.SampleMecanumDrive;

public class Auto_figure8 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;


        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(45));//starting stack is 12in in the y and 24in in the x

        //see here https://www.desmos.com/calculator/utqpmqcl4v
        Trajectory traj = drive.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(48, 36), 0)
                .splineTo(new Vector2d(84, 0), Math.toRadians(270))
                .splineTo(new Vector2d(48, -36), Math.toRadians(180))
                .splineTo(new Vector2d(0, 0), Math.toRadians(135))
                .splineTo(new Vector2d(-48, 36), Math.toRadians(180))
                .splineTo(new Vector2d(-84, 0), Math.toRadians(270))
                .splineTo(new Vector2d(-48, -36), 0)
                .splineTo(startPose.vec(), startPose.getHeading())
                .build();

        drive.followTrajectory(traj);

    }
}
