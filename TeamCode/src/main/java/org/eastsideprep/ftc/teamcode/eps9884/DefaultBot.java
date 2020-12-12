package org.eastsideprep.ftc.teamcode.eps9884;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DefaultBot implements Robot {

    private HardwareMap hardwareMap;
    private IMU imu;
    private DriveTrain driveTrain;
    OpMode opMode;
    Telemetry telemetry;

    /**
     * @param hardwareMap
     * @param telemetry
     */
    DefaultBot(HardwareMap hardwareMap, Telemetry telemetry, OpMode opMode) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.opMode = opMode;
    }

    public void init() {

        imu = new IMU(hardwareMap, telemetry);
        imu.init();

        driveTrain = new DriveTrain(hardwareMap, telemetry, true);
        driveTrain.init();

    }

    public void runMotors(double fL, double bL, double fR, double bR) {
        driveTrain.runMotors(fL, bL, fR, bR);

    }

    public void testTelemetry() {
        imu.testTelemetry();
        driveTrain.testTelemetry();
    }

    /**
     * Turns right by default, left if speed negative. Turns towards angle
     *
     * @param angle  target angle
     * @param speed  motor speed
     * @param margin margin for error
     */
    public void turnToAngle(Double angle, Double speed, Double margin) {
        if (!driveTrain.getRunningWithEncoders()) {
            driveTrain.runWithEncoders();
        }
       /* while (Math.abs(angle - imu.getAngle()) > margin, ){
            runMotors(speed, speed, -speed, -speed);

        }*/
    }


}
