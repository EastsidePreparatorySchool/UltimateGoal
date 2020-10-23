package org.eastsideprep.ftc.teamcode.eps9884;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DefaultBot implements Robot {

    private HardwareMap hardwareMap;
    IMU imu;
    Telemetry telemetry;

    DefaultBot(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

    }

    public void init() {

        imu = new IMU(hardwareMap, telemetry);
        imu.init();


    }

    public void testTelemetry(){
        imu.testTelemetry();

    }



}
