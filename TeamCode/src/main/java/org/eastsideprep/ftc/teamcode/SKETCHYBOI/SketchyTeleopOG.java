package org.eastsideprep.ftc.teamcode.SKETCHYBOI;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

//Controls:
//  Left stick    ->    Drive
//  Right stick   ->    Turn
//  Right trigger ->    Shoot ring
//  Left bumper   ->    Control arm
//  Right bumper  ->    Control claw

@TeleOp(name="Sketchy Boi Teleop ORIGINAL [actually do use]", group="SKETCHYBOI")

public class SketchyTeleopOG extends LinearOpMode {
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

        waitForStart();

        //robot.goShooter(0.88);
        //robot.goIntake(1);
        boolean wobbleDown = false;
        boolean grabberOpen = false;
        boolean blockersOpen = false;

        double driveFactor = 1;
        boolean slowMode = false;


        while(opModeIsActive()) {
            double lX = -gamepad1.left_stick_x;
            double lY = -gamepad1.left_stick_y;
            double rX = -gamepad1.right_stick_x;
            double rY = gamepad1.right_stick_y;

            a = gamepad1.a;
            b = gamepad1.b;
            x = gamepad1.x;
            y = gamepad1.y;
            l = gamepad1.left_bumper;
            r = gamepad1.right_bumper;
            zl = gamepad1.left_trigger;
            zr = gamepad1.right_trigger;

            //Mr. Mein's Math
            double dsAngle = Math.atan2(lX, lY);
            double dsWeight = Math.sqrt(lX * lX + lY * lY);
            double rotPower = rX;
            double rotWeight = Math.abs(rX);



            //make sure values are not greater than 1
            if (dsWeight + rotWeight > 1.0) {
                dsWeight /= dsWeight + rotWeight;
                rotPower /= dsWeight + rotWeight;
            }
            // finally, do a little math and put them into the motors


            robot.FrontLeftMotor.setPower(driveFactor*(Math.cos(dsAngle + Math.PI / 4) * dsWeight - rotPower * rotWeight));
            robot.BackRightMotor.setPower(driveFactor*(Math.cos(dsAngle + Math.PI / 4) * dsWeight + rotPower * rotWeight));
            robot.FrontRightMotor.setPower(driveFactor*(Math.cos(dsAngle - Math.PI / 4) * dsWeight + rotPower * rotWeight));
            robot.BackLeftMotor.setPower(driveFactor*(Math.cos(dsAngle - Math.PI / 4) * dsWeight - rotPower * rotWeight));

            if(rX == 0 && rY == 0 && lX == 0 && lY == 0){
                robot.FrontLeftMotor.setPower(0);
                robot.BackRightMotor.setPower(0);
                robot.FrontRightMotor.setPower(0);
                robot.BackLeftMotor.setPower(0);
            }

            if (a && slowMode) {
                driveFactor = 1;
            } else if (a && !slowMode) {
                driveFactor = 0.7;
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


            if(gamepad1.left_bumper && !wobbleDown){
                robot.ArmServo70.setPosition(0.3);
                wobbleDown = true;
                sleep(500);
            } else if(gamepad1.left_bumper && wobbleDown){
                robot.ArmServo70.setPosition(1);
                wobbleDown = false;
                sleep(500);
            }


            if(gamepad1.right_bumper && !grabberOpen){
                robot.openClaw();
                grabberOpen = true;
                sleep(500);
            } else if(gamepad1.right_bumper && grabberOpen){
                robot.closeClaw();
                grabberOpen = false;
                sleep(500);
            }

            if(zl > 0 && !blockersOpen){
                robot.extendRingBlockers();
                blockersOpen = true;
                sleep(500);
            } else if(zl > 0 && blockersOpen){
                robot.retractRingBlockers();
                blockersOpen = false;
                sleep(500);
            }

            if(b || zr > 0){
                robot.RingPushServo.setPosition(robot.servoMinPos);
            } else {
                robot.RingPushServo.setPosition(robot.servoMaxPos);
            }

            telemetry.addData("intake state", intakeState);
            telemetry.addData("shooter state", shooterState);
//            telemetry.addData("servo position", robot.RingPushServo.getPosition());
//            telemetry.addData("tx", tx);
//            telemetry.addData("ty", ty);
//            telemetry.addData("front left", robot.FrontLeftMotor.getPower());
//            telemetry.addData("front right", robot.FrontRightMotor.getPower());
//            telemetry.addData("back left", robot.BackLeftMotor.getPower());
//            telemetry.addData("back right", robot.BackRightMotor.getPower());
//            telemetry.addData("arm state", wobbleDown);
//            telemetry.addData("arm pos", robot.ArmServo70.getPosition());
            telemetry.addData("left blocker pos", robot.LeftRingBlockerServo.getPosition());
            telemetry.addData("right blocker pos", robot.RightRingBlockerServo.getPosition());

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
            robot.goShooter(.9);
        } else {
            robot.stopShooter();
        }
    }

}