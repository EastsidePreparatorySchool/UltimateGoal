//package org.eastsideprep.ftc.teamcode.SKETCHYBOI;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//@TeleOp (name = "Intake Speed Test", group = "SKETCHYBOI")
//public class IntakeSpeedTest extends LinearOpMode {
//    SketchyHardware robot = new SketchyHardware();
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        robot.init(hardwareMap);
//
//        telemetry.addData("say", "ready");
//
//        ElapsedTime runtime = new ElapsedTime();
//
//
//        double motorPower = 1;
//
//        waitForStart();
//
//        while( opModeIsActive() ){
//
//            if (gamepad1.a) {
//                motorPower = motorPower - 0.01;
//            }
//            if (gamepad1.y) {
//                motorPower = motorPower + 0.01;
//            }
//
//            if (gamepad1.b) {
//                robot.IntakeMotor.setPower(0);
//                robot.IntakeMotor2.setPower(0);
//            } else {
//                robot.IntakeMotor.setPower(motorPower);
//                robot.IntakeMotor2.setPower(1);
//            }
//
//            telemetry.addData("motor power", motorPower);
//            telemetry.update();
//
//            sleep(100);
//        }
//    }
//}
