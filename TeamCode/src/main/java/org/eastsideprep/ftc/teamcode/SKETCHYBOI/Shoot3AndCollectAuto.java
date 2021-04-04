package org.eastsideprep.ftc.teamcode.SKETCHYBOI;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="shoot rings then go collect more", group="SKETCHYBOI")
public class Shoot3AndCollectAuto extends LinearOpMode {

    //declare opmode members
    SketchyHardware robot = new SketchyHardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        //telemetry
        telemetry.addData("Status", "started");
        telemetry.update();

        waitForStart();

        robot.goShooter(0.85);
        robot.allDrive(-1, 1200);
        robot.turn(0.1, 200);
        sleep(2000);
        for (int x = 0; x <= 10; x++) {
            robot.pushRing();
            sleep(1000);
        }
        //stuff u want to do goes here
    }

}
