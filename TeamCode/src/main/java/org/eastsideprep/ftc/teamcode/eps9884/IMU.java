package org.eastsideprep.ftc.teamcode.eps9884;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class IMU {
    /**
     * IMU device
     */
    private BNO055IMU imu;
    private Orientation orientation;
    private Telemetry telemetry;
    /**
     * Angle in Radians
     */
    private Double angle;

    private BNO055IMU.CalibrationStatus calibStatus;

    public IMU(HardwareMap hardwareMap, Telemetry telemetry) {
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        this.telemetry = telemetry;
    }

    /**
     * Run this during initialization
     */
    public void init() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        parameters.loggingEnabled = true;
        parameters.mode = BNO055IMU.SensorMode.IMU;

        imu.initialize(parameters);
        calibStatus = imu.getCalibrationStatus();


    }

    public BNO055IMU.CalibrationStatus getCalibStatus() {
        calibStatus = imu.getCalibrationStatus();
        return calibStatus;

    }

    // more about IMU's https://stemrobotics.cs.pdx.edu/node/7265

    public Double getAngle() {
        Orientation imuAngularOrientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        Double changeInAngle;

        angle = imuAngularOrientation.firstAngle - 0.0;
        return angle;

    }

    public void testTelemetry() {
        telemetry.addAction(new Runnable() {
            @Override
            public void run() {
                getAngle();
                getCalibStatus();
            }
        });

        telemetry.addLine().addData("IMU CalibStat", new Func<String>() {
                    @Override
                    public String value() {
                        return getCalibStatus().toString();
                    }
                }
        );

        telemetry.addLine().addData("IMU Angle", new Func<String>() {
            @Override
            public String value() {

                return getAngle().toString();
            }

        });
    }

}
