package org.eastsideprep.ftc.teamcode.SKETCHYBOI;

import com.arcrobotics.ftclib.hardware.RevIMU;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.eastsideprep.ftc.teamcode.TrajanStuff412.YellowJacket19_2;
import org.eastsideprep.ftc.teamcode.TrajanStuff412.YellowJacket3_7;
import org.eastsideprep.ftc.teamcode.TrajanStuff412.util.Encoder;

public class Hardware412 {

    HardwareMap hwMap = null;

    //drive motors
    public YellowJacket19_2 FrontLeftMotorYJ = null;
    public YellowJacket19_2 FrontRightMotorYJ = null;
    public YellowJacket19_2 BackRightMotorYJ = null;
    public YellowJacket19_2 BackLeftMotorYJ = null;

    public YellowJacket3_7 IntakeMotorYJ = null;
    public YellowJacket3_7 ShooterMotorYJ = null;

    public Encoder LeftEncoder = null;
    public Encoder RightEncoder = null;
    public Encoder FrontEncoder = null;

    RevIMU revIMU = null;

    public Hardware412() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hwMap) {
        FrontLeftMotorYJ = new YellowJacket19_2(hwMap, "FrontLeftMotor");
        FrontRightMotorYJ = new YellowJacket19_2(hwMap, "FrontRightMotor");
        BackRightMotorYJ = new YellowJacket19_2(hwMap, "BackRightMotor");
        BackLeftMotorYJ = new YellowJacket19_2(hwMap, "BackLeftMotor");

        IntakeMotorYJ = new YellowJacket3_7(hwMap, "IntakeMotor");
        ShooterMotorYJ = new YellowJacket3_7(hwMap, "ShooterMotor");

        LeftEncoder = new Encoder(hwMap.get(DcMotorEx.class, "FrontLeftMotor"));
        RightEncoder = new Encoder(hwMap.get(DcMotorEx.class, "FrontRightMotor"));
        FrontEncoder = new Encoder(hwMap.get(DcMotorEx.class, "BackLeftMotor"));

        FrontLeftMotorYJ.setInverted(true);
        FrontRightMotorYJ.setInverted(true);
        BackRightMotorYJ.setInverted(true);
        BackLeftMotorYJ.setInverted(true);

        //revIMU = new RevIMU(hwMap, "imu");
        //revIMU.init();
    }
}


