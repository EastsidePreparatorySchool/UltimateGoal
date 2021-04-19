package org.eastsideprep.ftc.teamcode.SKETCHYBOI;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="testing 4 cool servo arm", group="SKETCHYBOI")

public class ServoTestTeleop extends LinearOpMode {

    SketchyHardware robot = new SketchyHardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData("Say", "ready");

        ElapsedTime runtime = new ElapsedTime();

        double servoPos = 0.5;

        waitForStart();

        while(opModeIsActive()) {
            robot.ArmServo70.setPosition(servoPos);

            if(gamepad1.left_trigger > 0){
                servoPos = servoPos - 0.01;
            }

            if(gamepad1.right_trigger > 0){
                servoPos = servoPos + 0.01;
            }

            telemetry.addData("servoPos", servoPos);
            telemetry.update();
            sleep(40);

        }

    }
}
