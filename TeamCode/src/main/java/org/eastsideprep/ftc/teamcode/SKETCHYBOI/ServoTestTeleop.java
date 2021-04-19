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
            
//            if(gamepad1.a){
//                robot.ArmServo70.setPosition(0);
//            }

            if(gamepad1.y){
                robot.ArmServo70.setPosition(0.01);
            }

            else if(gamepad1.b){
                robot.ArmServo70.setPosition(0.8);
            }

            else if(gamepad1.x){
                robot.ArmServo70.setPosition(gamepad1.left_trigger);
            }

            telemetry.addData("servo position", robot.ArmServo70.getPosition());
            telemetry.update();

            sleep(40);

        }

    }
}
