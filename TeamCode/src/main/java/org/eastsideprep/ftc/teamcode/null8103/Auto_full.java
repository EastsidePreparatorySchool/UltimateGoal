/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.eastsideprep.ftc.teamcode.null8103;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.eastsideprep.ftc.teamcode.null8103.drive.SampleMecanumDrive;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.concurrent.TimeUnit;

@Autonomous(name = "full auto")

public class Auto_full extends LinearOpMode {

    RobotHardware robot;
    SampleMecanumDrive drive;

    @Override
    public void runOpMode() {

        /* before starting:
        - init hardware
        - set up phone camera and vision pipeline
        - see number of rings
        - generate chosen path based on number of rings
         */

        robot = new RobotHardware();
        robot.init(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvInternalCamera phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        Auto_ring_detection.RingDetectorPipeline ringDetectorPipeline = new Auto_ring_detection.RingDetectorPipeline();

        phoneCam.setPipeline(ringDetectorPipeline);
        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                //can remove later
                phoneCam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }
        });

        drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startPose = new Pose2d(-48, -24, Math.toRadians(90));

        Pose2d goalShootingPose = new Pose2d(0, -36, Math.toRadians(210));
        Pose2d powerShootingPose = new Pose2d(0, -28, Math.toRadians(220));

        Pose2d secondWobblePose = new Pose2d(-36, -48, Math.toRadians(180));

        Pose2d endPose = new Pose2d(12, -36, Math.toRadians(90));

        drive.setPoseEstimate(startPose);


        //0 rings -> goal A
        TrajectoryBuilder trajZero = drive.trajectoryBuilder(startPose)
                .splineTo(powerShootingPose.vec(), powerShootingPose.getHeading())
                .addDisplacementMarker(() -> {
                    powerShotSequence();
                })
                .splineTo(new Vector2d(16, -64), 0)
                .addDisplacementMarker(() -> {
                    robot.lowerOpenWobble();
                })
                .splineTo(secondWobblePose.vec(), secondWobblePose.getHeading())
                .addDisplacementMarker(() -> {
                    robot.closeRaiseWobble();
                })
                .splineTo(new Vector2d(18, -56), 0)
                .addDisplacementMarker(() -> {
                    robot.lowerOpenWobble();
                })
                .splineTo(endPose.vec(), endPose.getHeading())
                .addDisplacementMarker(() -> {
                    robot.closeRaiseWobble();
                });

        //1 ring -> goal B
        //TODO: add spline to intake starter stack
        TrajectoryBuilder trajOne = drive.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(-20, -20), Math.toRadians(-80))//dont hit the starter stack
                .splineTo(powerShootingPose.vec(), powerShootingPose.getHeading())
                .addDisplacementMarker(() -> {
                    powerShotSequence();
                })
                .splineTo(new Vector2d(16, -64), 0)
                .addDisplacementMarker(() -> {
                    robot.lowerOpenWobble();
                })
                .splineTo(secondWobblePose.vec(), secondWobblePose.getHeading())
                .addDisplacementMarker(() -> {
                    robot.closeRaiseWobble();
                })
                .splineTo(new Vector2d(18, -56), 0)
                .addDisplacementMarker(() -> {
                    robot.lowerOpenWobble();
                })
                .splineTo(endPose.vec(), endPose.getHeading())
                .addDisplacementMarker(() -> {
                    robot.closeRaiseWobble();
                });

        TrajectoryBuilder trajFour = drive.trajectoryBuilder(startPose);

        Trajectory chosenTraj;

        int numRings = ringDetectorPipeline.getResult();

        if (numRings == 4) {
            chosenTraj = trajFour.build();
        } else if (numRings == 1) {
            chosenTraj = trajOne.build();
        } else {
            chosenTraj = trajZero.build();
        }

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("num rings", "" + ringDetectorPipeline.getResult());
            telemetry.update();
            drive.followTrajectory(chosenTraj);
        }
    }

    Timing.Timer powerShotTimer = new Timing.Timer(3500, TimeUnit.MILLISECONDS);
    double pusherEnd = 0.75;

    public void powerShotSequence() {
        telemetry.addData("log", "shoot power shots!");
//        robot.shooter.set(0.9);
//        robot.RingPushServo.setPosition(0);
//        powerShotTimer.start();
//        if (powerShotTimer.currentTime() > 2000) {
//            robot.RingPushServo.setPosition(pusherEnd);//shoot first ring
//        } else if (powerShotTimer.currentTime() > 2500) {
//            robot.RingPushServo.setPosition(0);
//            turnLeft(750, 0.4);
//        } else if (powerShotTimer.currentTime() > 2750) {
//            robot.RingPushServo.setPosition(pusherEnd);//shoot second ring
//
//        } else if (powerShotTimer.currentTime() > 3000) {
//            robot.RingPushServo.setPosition(0);
//        } else if (powerShotTimer.currentTime() > 3250) {
//            robot.RingPushServo.setPosition(0.8);
//
//        } else if (powerShotTimer.done()) {
//            robot.RingPushServo.setPosition(0);
//            robot.shooter.set(0);
//        }
    }

    public void turnLeft(long time, double power) {
        Timing.Timer timer = new Timing.Timer(time, TimeUnit.MILLISECONDS);
        do {
            robot.leftFront.set(-power);
            robot.rightBack.set(power);
            robot.leftBack.set(-power);
            robot.rightFront.set(power);
        } while (!timer.done());
        robot.leftFront.set(0);
        robot.rightBack.set(0);
        robot.leftBack.set(0);
        robot.rightFront.set(0);

    }
}