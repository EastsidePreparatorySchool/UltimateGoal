package org.eastsideprep.ftc.teamcode.SKETCHYBOI;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Basic Autonomous for Sketchy Boi", group="SKETCHYBOI")
public class SketchyAutonomous extends LinearOpMode {

    //declare opmode members
    SketchyHardware robot = new SketchyHardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        //telemetry
        telemetry.addData("Status", "started");
        telemetry.update();

        waitForStart();

        //stuff u want to do goes here
    }

}
