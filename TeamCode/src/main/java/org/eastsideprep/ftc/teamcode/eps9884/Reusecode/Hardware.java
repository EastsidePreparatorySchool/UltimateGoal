package org.eastsideprep.ftc.teamcode.eps9884.Reusecode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Hardware {



    /**
     * Hardware definitions for everest
     * hi
     * testing123
     */
    public class HardwareE {
        /* Public OpMode members. */

        double [] rotationArray;
        Chassis chassis;
        Grabber claw;
        Grabber foundation;
        Servo gs1;
        Servo gs2;

        /* local OpMode members. */
        HardwareMap hwMap = null;
        private ElapsedTime period = new ElapsedTime();

        /* Constructor */
        public HardwareE() {

        }

        /* Initialize standard Hardware interfaces */
        public void init(HardwareMap ahwMap) {
            // Save referen ce to Hardware map
            hwMap = ahwMap;


            //Set up the Chassis
            //chassis = new Chassis(hwMap, "frontLeftMotor", "frontRightMotor", "backLeftMotor", "backRightMotor");
//        chassis = new Chassis(hwMap, "frontLeftMotor", "frontRightMotor", "backLeftMotor", "backRightMotor");
            chassis = new Chassis(hwMap, "LF", "RF", "LB", "RB");

            chassis.setDirections(
                    DcMotor.Direction.FORWARD,
                    DcMotor.Direction.REVERSE,
                    DcMotor.Direction.FORWARD,
                    DcMotor.Direction.REVERSE
            );
            chassis.setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            chassis.setModes(DcMotor.RunMode.RUN_USING_ENCODER);
            chassis.setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);

            claw = new Grabber(
                    new Mechanism(hwMap, "GS1", 0.48, 0.55),
                    new Mechanism(hwMap, "GS2", 0.52, 0.45)
            );

//        foundation = new Grabber(
//                new Mechanism(hwMap, "FS1", ),
//                new Mechanism(hwMap, "FS2", )
//        );

        }

        public double[] getDrivePowersFromAngle(double angle) {
            double[] unscaledPowers = new double[4];
            unscaledPowers[0] = Math.sin(angle + Math.PI / 4);
            unscaledPowers[1] = Math.cos(angle + Math.PI / 4);
            unscaledPowers[2] = unscaledPowers[1];
            unscaledPowers[3] = unscaledPowers[0];
            return unscaledPowers;
        }

    }


}
