package org.eastsideprep.ftc.teamcode.SKETCHYBOI;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="sketchy boi wheel test", group="SKETCHYBOI")
public class SketchyWheelTest extends LinearOpMode {

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
