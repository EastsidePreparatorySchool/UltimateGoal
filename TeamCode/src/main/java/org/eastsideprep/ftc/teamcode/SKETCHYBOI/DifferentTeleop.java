package org.eastsideprep.ftc.teamcode.SKETCHYBOI;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Sketchy Boi Teleop with DIFFERENT code", group="SKETCHYBOI")

public class DifferentTeleop extends LinearOpMode {
    //Declare opmode members.

    SketchyHardware robot = new SketchyHardware();

    //Code to run once at init
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData("Say", "ready");

        ElapsedTime runtime= new ElapsedTime();
//        double lX;
//        double lY;
//        double rX;
//        double rY;

        boolean a;
        boolean b;
        boolean x;
        boolean y;
        boolean l;
        boolean r;
        double zl;
        double zr;

        int tx = 0;
        int ty = 0;

        boolean intakeState = true;
        boolean shooterState = true;

        double servoMax = 1;
        double servoMin = 0.80; //less is further

        waitForStart();

        while(opModeIsActive()) {
            double lX = gamepad1.left_stick_x;
            double lY = gamepad1.left_stick_y;
            double rX = gamepad1.right_stick_x;
            double rY = gamepad1.right_stick_y;

            a = gamepad1.a;
            b = gamepad1.b;
            x = gamepad1.x;
            y = gamepad1.y;
            l = gamepad1.left_bumper;
            r = gamepad1.right_bumper;
            zl = gamepad1.left_trigger;
            zr = gamepad1.right_trigger;

            //code from two years ago
            float x1 = gamepad1.left_stick_x;
            float y1 = -gamepad1.left_stick_y; // Negate to get +y forward.
            float rotation = -gamepad1.right_stick_x;
            float speedControl = 0.5f * (1.0f + gamepad1.left_trigger);
            double biggestControl = Math.sqrt(x1 * x1 + y1 * y1);
            double biggestWithRotation = Math.sqrt(x1 * x1 + y1 * y1 + rotation * rotation);

            double angle = Math.atan2(y1, -x1) - Math.PI / 2.0;

            double[] powers = robot.getDrivePowersFromAngle(angle);
            double pow2 = 0.0;
            for (int i = 0; i < robot.allMotors.length; i++) {
                double pow = powers[i] * biggestControl + rotation * robot.rotationArray[i];
                powers[i] = pow;
                pow2 += pow * pow;
            }

            if (biggestWithRotation != 0.0) {
                double scale = Math.sqrt(pow2);
                for (int i = 0; i < robot.allMotors.length; i++) {
                    robot.allMotors[i].setPower(
                            powers[i] / scale * biggestWithRotation * speedControl);
                }
            } else {
                for (int i = 0; i < robot.allMotors.length; i++)
                    robot.allMotors[i].setPower(0.0);
            }

            if(x && tx > 4){
                intakeState = !intakeState;
                setIntake(intakeState);
                tx = 0;
            }

            if(y && ty > 4){
                shooterState = !shooterState;
                setShooter(shooterState);
                tx = 0;
            }

            if(b || zr > 0){
                robot.RingPushServo.setPosition(servoMin);
            } else {
                robot.RingPushServo.setPosition(servoMax);
            }

            telemetry.addData("intake state", intakeState);
            telemetry.addData("shooter state", shooterState);
            telemetry.addData("servo position", robot.RingPushServo.getPosition());
            telemetry.addData("tx", tx);
            telemetry.addData("ty", ty);
            telemetry.addData("front left", robot.FrontLeftMotor.getPower());
            telemetry.addData("front right", robot.FrontRightMotor.getPower());
            telemetry.addData("back left", robot.BackLeftMotor.getPower());
            telemetry.addData("back right", robot.BackRightMotor.getPower());

            telemetry.update();

            tx = tx + 1;
            ty = ty + 1;
            sleep(40);
        }


    }

    public void setIntake(boolean intakeState){
        if(intakeState){
            robot.goIntake(1);
        } else {
            robot.stopIntake();
        }
    }

    public void setShooter(boolean shooterState){
        if(shooterState){
            robot.goShooter(1);
        } else {
            robot.stopShooter();
        }
    }

}