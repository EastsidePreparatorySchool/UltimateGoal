package org.eastsideprep.ftc.teamcode.SKETCHYBOI;

//This is the Hardware.java file for Robotics 2020-21 Ultimate Goal.
//The robot's name is Sketchy Boi.

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.arcrobotics.ftclib.hardware.RevIMU;

import org.eastsideprep.ftc.teamcode.TrajanStuff412.YellowJacket19_2;

import org.eastsideprep.ftc.teamcode.TrajanStuff412.util.*;

public class SketchyHardware {
    public DcMotor FrontLeftMotor = null;
    public DcMotor FrontRightMotor = null;
    public DcMotor BackLeftMotor = null;
    public DcMotor BackRightMotor = null;
    public DcMotor [] allMotors;
    public DcMotor ShooterMotor = null;
    public DcMotor FrontIntakeMotor = null;
    public DcMotor IntakeMotor1 = null;
    public DcMotor IntakeMotor2 = null;
//    public DcMotor IntakeMotor2 = null;

    public Servo RingPushServo = null;

    public Servo ArmServo70 = null;
    public Servo GrabberServo = null;

    public Servo LeftRingBlockerServo = null;
    public Servo RightRingBlockerServo = null;

    public RevIMU IMU = null;

    public double IntakeSpeed = 1;
    public double ShooterSpeed = 1;

    public double servoMinPos = 0.8;
    public double servoMaxPos = 1;

    double [] rotationArray;

    //Local opMode members.
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    //Constructor
    public SketchyHardware() {
        //Nothing :)
    }

    public void init(HardwareMap ahwMap) {
        //Save references to hardware map
        hwMap = ahwMap;

        //define and init motors
        FrontLeftMotor = hwMap.dcMotor.get("LF");
        FrontRightMotor = hwMap.dcMotor.get("RF");
        BackLeftMotor = hwMap.dcMotor.get("LB");
        BackRightMotor = hwMap.dcMotor.get("RB");
//
//        FrontLeftMotorYJ = new YellowJacket19_2(hwMap, "FrontLeftMotor");
//        FrontRightMotorYJ = new YellowJacket19_2(hwMap, "FrontRightMotor");
//        BackLeftMotorYJ = new YellowJacket19_2(hwMap, "BackLeftMotor");
//        BackRightMotorYJ = new YellowJacket19_2(hwMap, "BackRightMotor");

//        LeftEncoder = new Encoder(hwMap.get(DcMotorEx.class, "FrontLeftMotor"));
//        RightEncoder = new Encoder(hwMap.get(DcMotorEx.class, "FrontRightMotor"));
//        FrontEncoder = new Encoder(hwMap.get(DcMotorEx.class, "BackLeftMotor"));

        ShooterMotor = hwMap.dcMotor.get("ShooterMotor");
        FrontIntakeMotor = hwMap.dcMotor.get("FrontIntakeMotor");
        IntakeMotor1 = hwMap.dcMotor.get("IntakeMotor1");
        IntakeMotor2 = hwMap.dcMotor.get("IntakeMotor2");
//        IntakeMotor2 = hwMap.dcMotor.get("IntakeMotor2");

        RingPushServo = hwMap.servo.get("RingPushServo");

        ArmServo70 = hwMap.servo.get("WobblePivotServo");
        GrabberServo = hwMap.servo.get("WobbleGrabberServo");

        LeftRingBlockerServo = hwMap.servo.get("LeftRingBlockerServo");
        RightRingBlockerServo = hwMap.servo.get("RightRingBlockerServo");

        allMotors = new DcMotor[] {
                FrontLeftMotor, FrontRightMotor, BackLeftMotor, BackRightMotor
        };

        rotationArray = new double[] {-1.0, 1.0, -1.0, 1.0};

//        BackLeftMotor.setDirection(DcMotor.Direction.FORWARD);
//        BackRightMotor.setDirection(DcMotor.Direction.REVERSE);
//        FrontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
//        FrontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        BackLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        BackRightMotor.setDirection(DcMotor.Direction.FORWARD);
        FrontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        FrontRightMotor.setDirection(DcMotor.Direction.FORWARD);

        ShooterMotor.setDirection(DcMotor.Direction.REVERSE); //change if the motor is going the wrong way
        FrontIntakeMotor.setDirection(DcMotor.Direction.FORWARD); //see above
        IntakeMotor1.setDirection(DcMotor.Direction.FORWARD);
        IntakeMotor2.setDirection(DcMotor.Direction.FORWARD);
        //        IntakeMotor2.setDirection(DcMotor.Direction.FORWARD);

        ShooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrontIntakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        IntakeMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        IntakeMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//        IntakeMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


        for (DcMotor m : allMotors) {
            m.setPower(0.0);
//            m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //this is good for auto, but is it good for driver control?
        }

    }

    public double[] getDrivePowersFromAngle(double angle) {
        double[] unscaledPowers = new double[4];
        unscaledPowers[0] = Math.sin(angle + Math.PI / 4);
        unscaledPowers[1] = Math.cos(angle + Math.PI / 4);
        unscaledPowers[2] = unscaledPowers[1];
        unscaledPowers[3] = unscaledPowers[0];
        return unscaledPowers;
    }

    void threadSleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            //do nothing
        }
    }
    /*
    VARIOUS FUNCTIONS EVEREST WROTE FOR AUTONOMOUS ROUTINES LAST YEAR BUT IS TOO LAZY TO REWRITE THEM
    (OR FIX THE FORMATTING FOR THAT MATTER):
            - allDrive: sets all motors to a given power, condensing code needed to drive.
     - turn: turns in place at a given power for a given number of
    milliseconds. There is no way to input degrees.
     - spinTurn: a project of Ben's that is still in progress. When finished, it will
    allow the robot to turn on a pivot instead of in place.
            - garageLift and garagePlace: turns the CR servos on the garage mechanism in
    the directions needed to lift or release a block for a given number of milliseconds.
     */

    public void allDrive(double power, int milliseconds){
        FrontLeftMotor.setPower(power);
        BackLeftMotor.setPower(power);
        FrontRightMotor.setPower(power);
        BackRightMotor.setPower(power);

        threadSleep(milliseconds);

        FrontLeftMotor.setPower(0);
        BackLeftMotor.setPower(0);
        FrontRightMotor.setPower(0);
        BackRightMotor.setPower(0);
    }

    public void turn(double power, int milliseconds){
        //Front motors
        FrontLeftMotor.setPower(-power);
        FrontRightMotor.setPower(power);
        //Back motors
        BackLeftMotor.setPower(-power);
        BackRightMotor.setPower(power);

        threadSleep(milliseconds);

        FrontLeftMotor.setPower(0);
        BackLeftMotor.setPower(0);
        FrontRightMotor.setPower(0);
        BackRightMotor.setPower(0);
    }

    public void spinTurn(double power, int milliseconds){
        FrontLeftMotor.setPower(-power);
        FrontRightMotor.setPower(power);
        //Back motors
        BackLeftMotor.setPower(power);
        BackRightMotor.setPower(power);

        threadSleep(milliseconds);

        FrontLeftMotor.setPower(0);
        FrontRightMotor.setPower(0);
        //Back motors
        BackLeftMotor.setPower(0);
        BackRightMotor.setPower(0);
    }

    public void strafe (double power, int milliseconds) {
        FrontLeftMotor.setPower(power);
        FrontRightMotor.setPower(-1*power);
        BackLeftMotor.setPower(-1*power);
        BackRightMotor.setPower(power);

        threadSleep(milliseconds);

        FrontLeftMotor.setPower(0.0);
        FrontRightMotor.setPower(0.0);
        BackLeftMotor.setPower(0.0);
        BackRightMotor.setPower(0.0);

    }

    public void pushRing() {
        RingPushServo.setPosition(servoMinPos);
        threadSleep(1000);
        RingPushServo.setPosition(servoMaxPos);
    }

    /* FUNCTIONS THAT EVEREST WROTE THIS YEAR */
    public void goIntake(double power){
        FrontIntakeMotor.setPower(power);
        IntakeMotor1.setPower(power);
        IntakeMotor2.setPower(power);
//        IntakeMotor2.setPower(1 * power); //75% Because Fino Said So
    }

    public void stopIntake(){
        goIntake(0);
    }

    public void goShooter(double power){
        ShooterMotor.setPower(power);
    }

    public void stopShooter(){
        ShooterMotor.setPower(0);
    }

    public void retractWobbleGoal() { ArmServo70.setPosition(1);}

    public void extendWobbleGoal() { ArmServo70.setPosition(0.3);}

    public void openClaw() { GrabberServo.setPosition(0.2); };

    public void closeClaw() { GrabberServo.setPosition(1); };

    public void extendRingBlockers() {
        LeftRingBlockerServo.setPosition(0.9);
        RightRingBlockerServo.setPosition(0);
    };

    public void retractRingBlockers() {
        LeftRingBlockerServo.setPosition(0.5);
        RightRingBlockerServo.setPosition(0.4);
    }
}
