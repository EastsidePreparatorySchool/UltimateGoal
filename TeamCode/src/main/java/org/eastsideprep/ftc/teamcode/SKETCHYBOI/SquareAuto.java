package org.eastsideprep.ftc.teamcode.SKETCHYBOI;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="S Q U A R E", group="SKETCHYBOI")
public class SquareAuto extends LinearOpMode {

    //declare opmode members
    SketchyHardware robot = new SketchyHardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        //telemetry
        telemetry.addData("Status", "started");
        telemetry.update();

        waitForStart();

        robot.allDrive(0.5, 1000);
        robot.strafe(0.5, 1000);
        robot.allDrive(-0.5, 1000);
        robot.strafe(-0.5, 1000);

        //stuff u want to do goes here
    }

}
