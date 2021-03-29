package org.eastsideprep.ftc.teamcode.SKETCHYBOI;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Servo Position Script", group="SKETCHYBOI")

public class ServoPosScript extends LinearOpMode {

    SketchyHardware robot = new SketchyHardware();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("Servo position", robot.RingPushServo.getPosition());
            telemetry.update();
            sleep(40);
        }


    }

}
