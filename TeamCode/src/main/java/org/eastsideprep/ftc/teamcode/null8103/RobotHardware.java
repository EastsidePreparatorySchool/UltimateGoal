package org.eastsideprep.ftc.teamcode.null8103;

import com.arcrobotics.ftclib.hardware.RevIMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class RobotHardware {

    HardwareMap hwMap = null;

    //drive motors
    public YellowJacket19_2 leftFront = null;
    public YellowJacket19_2 rightFront = null;
    public YellowJacket19_2 rightBack = null;
    public YellowJacket19_2 leftBack = null;

    public YellowJacket3_7 intake = null;

    RevIMU revIMU = null;

    public RobotHardware() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hwMap) {
        leftFront = new YellowJacket19_2(hwMap, "LF");
        rightFront = new YellowJacket19_2(hwMap, "RF");
        rightBack = new YellowJacket19_2(hwMap, "RB");
        leftBack = new YellowJacket19_2(hwMap, "LB");

        intake = new YellowJacket3_7(hwMap, "intake");

        leftFront.setInverted(true);
        rightFront.setInverted(true);
        rightBack.setInverted(true);
        leftBack.setInverted(true);

        //revIMU = new RevIMU(hwMap, "imu");
        //revIMU.init();
    }
}


