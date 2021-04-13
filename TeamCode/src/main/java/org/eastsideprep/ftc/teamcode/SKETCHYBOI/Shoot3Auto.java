package org.eastsideprep.ftc.teamcode.SKETCHYBOI;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="go forward and shoot rings (works)", group="SKETCHYBOI")
public class Shoot3Auto extends LinearOpMode {

    //declare opmode members
    SketchyHardware robot = new SketchyHardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        //telemetry
        telemetry.addData("Status", "started");
        telemetry.update();

        waitForStart();

        robot.turn(-0.1, 10); //turn so it's at the right direction and we don't have to do the twerk thing

        robot.goShooter(0.8);
        robot.allDrive(-1, 1200);
        robot.turn(0.1, 200);
        sleep(2000);
        for (int x = 0; x <= 4 ; x++) {
            robot.pushRing();
            sleep(1000);
        }
        robot.allDrive(-0.5, 450);
        //stuff u want to do goes here
    }

}
