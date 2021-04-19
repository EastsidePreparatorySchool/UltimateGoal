package org.eastsideprep.ftc.teamcode.null8103;

import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class RobotHardware {

    HardwareMap hwMap = null;

    //drive motors
    public YellowJacket19_2 leftFront = null;
    public YellowJacket19_2 rightFront = null;
    public YellowJacket19_2 rightBack = null;
    public YellowJacket19_2 leftBack = null;

    public YellowJacket3_7 front_intake = null;
    public YellowJacket5_2 top_intake1 = null;
    public YellowJacket5_2 top_intake2 = null;

    public MatrixMotor shooter = null;

    public SimpleServo RingPushServo = null;

    public SimpleServo wobblePivot = null;
    public SimpleServo wobbleGrabber = null;

    RevIMU revIMU = null;

    public RobotHardware() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hwMap) {
        leftFront = new YellowJacket19_2(hwMap, "LF");
        rightFront = new YellowJacket19_2(hwMap, "RF");
        rightBack = new YellowJacket19_2(hwMap, "RB");
        leftBack = new YellowJacket19_2(hwMap, "LB");

        front_intake = new YellowJacket3_7(hwMap, "FrontIntakeMotor");
        top_intake1 = new YellowJacket5_2(hwMap, "IntakeMotor1");
        top_intake2 = new YellowJacket5_2(hwMap, "IntakeMotor2");

        shooter = new MatrixMotor(hwMap, "ShooterMotor");

        RingPushServo = new SimpleServo(hwMap, "RingPushServo");

        wobblePivot = new SimpleServo(hwMap, "WobblePivotServo");
        wobbleGrabber = new SimpleServo(hwMap, "WobbleGrabberServo");

        leftFront.setInverted(true);
        rightFront.setInverted(true);
        rightBack.setInverted(true);
        leftBack.setInverted(true);

        front_intake.setInverted(false);
        top_intake1.setInverted(false);
        top_intake2.setInverted(false);
        shooter.setInverted(true);

        //revIMU = new RevIMU(hwMap, "imu");
        //revIMU.init();
    }
}


