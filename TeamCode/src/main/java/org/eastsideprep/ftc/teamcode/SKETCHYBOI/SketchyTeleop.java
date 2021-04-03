package org.eastsideprep.ftc.teamcode.SKETCHYBOI;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Sketchy Boi Teleop", group="SKETCHYBOI")

public class SketchyTeleop extends LinearOpMode {
    //Declare opmode members.

    SketchyHardware robot = new SketchyHardware();

    //Code to run once at init
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData("Say", "ready");

        ElapsedTime runtime= new ElapsedTime();
        double lX;
        double lY;
        double rX;
        double rY;

        boolean a;
        boolean b;
        boolean x;
        boolean y;
        boolean l;
        boolean r;
        double zl;
        double zr;

        boolean intakeState = true;
        boolean shooterState = true;

        waitForStart();

        while(opModeIsActive()) {
            lX = gamepad1.left_stick_x;
            lY = gamepad1.left_stick_y;
            rX = gamepad1.right_stick_x;
            rY = gamepad1.right_stick_y;

            a = gamepad1.a;
            b = gamepad1.b;
            x = gamepad1.x;
            y = gamepad1.y;
            l = gamepad1.left_bumper;
            r = gamepad1.right_bumper;
            zl = gamepad1.left_trigger;
            zr = gamepad1.right_trigger;

            //Mein's Math
            double dsAngle = Math.atan2(lX, lY);
            double dsWeight = Math.sqrt(lX * lX + lY * lY);
            double rotPower = rX;
            double rotWeight = Math.abs(rX);

            rotPower = rotPower * 0.6;

            //make sure values are not greater than 1

            if (dsWeight + rotWeight > 1.0) {
                dsWeight /= dsWeight + rotWeight;
                rotPower /= dsWeight + rotWeight;
            }
            // finally, do a little math and put them into the motors


            robot.FrontLeftMotor.setPower(Math.cos(dsAngle + Math.PI / 4) * dsWeight - rotPower * rotWeight);
            robot.BackRightMotor.setPower(Math.cos(dsAngle + Math.PI / 4) * dsWeight + rotPower * rotWeight);
            robot.FrontRightMotor.setPower(Math.cos(dsAngle - Math.PI / 4) * dsWeight + rotPower * rotWeight);
            robot.BackLeftMotor.setPower(Math.cos(dsAngle - Math.PI / 4) * dsWeight - rotPower * rotWeight);

            if(rX == 0 && rY == 0 && lX == 0 && lY == 0){
                robot.allDrive(0.0, 0);
            }

            if(x){
                intakeState = !intakeState;
            }

            if(y){
                shooterState = !shooterState;
            }

            if(intakeState){
                robot.goIntake(1);
            } else {
                robot.stopIntake();
            }

            if(shooterState){
                robot.goShooter(1);
            } else {
                robot.stopShooter();
            }

            telemetry.addData("intake state", intakeState);
            telemetry.addData("shooter state", shooterState);
            telemetry.update();

            if(a){
                robot.RingPushServo.setPosition(robot.RingPushServo.getPosition() + 5);
            }

            sleep(40);
        }


    }

}