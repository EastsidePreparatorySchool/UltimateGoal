//package org.eastsideprep.ftc.teamcode.SKETCHYBOI;
//
//import com.arcrobotics.ftclib.drivebase.MecanumDrive;
//import com.arcrobotics.ftclib.gamepad.GamepadEx;
//import com.arcrobotics.ftclib.gamepad.GamepadKeys;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//
//
//@TeleOp(name = "trajan's teleop")
//
////simple drive teleop
//public class TrajanTeleop extends LinearOpMode {
//
//    /* Declare OpMode members. */
//    SketchyHardware robot = new SketchyHardware();
//
//    @Override
//    public void runOpMode() {
//
//        robot.init(hardwareMap);
//        /* Initialize the hardware variables.
//         * The init() method of the hardware class does all the work here
//         */
//
//        telemetry.addData("Say", "Ready");
//        telemetry.update();
//
//        // Wait for the game to start (driver presses PLAY)
//        waitForStart();
//
//        MecanumDrive mecanumDrive = new MecanumDrive(robot.FrontLeftMotorEx, robot.FrontRightMotorEx, robot.BackLeftMotorEx, robot.BackRightMotorEx);
//        GamepadEx gamepad = new GamepadEx(gamepad1);
//
//
//        //double speedControl;
//
//        //both of these are tuned manually to driver preference
//        //used in the ether function to map gamepad -> drivetrain controls
//        double driveGain = 0.6;
//        double turnGain = 0.3;
//
//        double ySpeed, xSpeed, turnSpeed, gyroAngle;
//        // run until the end of the match (driver presses STOP)
//
//        boolean isIntakeOn = false;
//
//        while (opModeIsActive()) {
////            if (gamepad.getButton(GamepadKeys.Button.LEFT_BUMPER)) {
////                speedControl = 0.4;
////            } else {
////                speedControl = 1;
////            }
//            xSpeed = ether(gamepad.getLeftX(), driveGain);
//            ySpeed = ether(gamepad.getLeftY(), driveGain);
//            turnSpeed = ether(gamepad.getRightX(), turnGain);
//            //gyroAngle = Math.toDegrees(robot.revIMU.getAbsoluteHeading() * Math.PI + Math.PI);//imu data is a double from -1 to 1, convert to 0 to 2pi
//            mecanumDrive.driveRobotCentric(xSpeed, ySpeed, turnSpeed, true);
//            //mecanumDrive.driveFieldCentric(xSpeed, ySpeed, turnSpeed, gyroAngle, true);//squaring inputs is more precise
//            //telemetry.addData("imu data", gyroAngle);
//
////          i think this is trajans intake code but we're not using it
////            if (gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.8) {
////                robot.intake.set(1);
////            }else{
////                robot.intake.stopMotor();
////            }
////            telemetry.addData("intake current", robot.intake.getCurrent());
//
////            robot.intake.pidWrite(1);
//
//            telemetry.update();
//        }
//    }
//
//    //see graph at https://www.desmos.com/calculator/dx7yql2ekh
//    public static double ether(double x, double p) {
//        double min = 0.2; //this means that very small joystick movements give enough power to overcome friction
//        return Math.min(min + (1 - min) * (p * Math.pow(x, 3) + (1 - p) * x), 1);//max power is 1
//    }
//}